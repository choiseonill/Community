package com.ktds.community.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.community.vo.CommunityVO;
import com.ktds.member.constants.Member;
import com.ktds.member.vo.MemberVO;
import com.ktds.util.DownloadUtil;

import javafx.geometry.Orientation;

@Controller
public class CommunityController {
	//로그 생성!!!!!	
	private final Logger logger =
			LoggerFactory.getLogger(CommunityController.class);
	
	private CommunityService communityService;
	
	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}
	
	@RequestMapping("/")
	public ModelAndView viewListPage() {
		
		ModelAndView view = new ModelAndView();	
		
		//web-inf/community/list.jsp
		view.setViewName("community/list");		
		List<CommunityVO> communityList = communityService.getAll();
		view.addObject("communityList", communityList);	
		
		return view;
	}
		
	/**
	@GetMapping("/write")
	==> 이렇게도 매핑명 부여할 수 있음
	public ModelAndView enrollNewContents() {
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");		
		return view;
	} 이거말고!!!!
	페이지를 보여주기만 할 꺼여서 리턴타입이 스트링이래
	*/
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String viewWritePage() {
		return "community/write";
	}
	
	/**
	 * 파라미터 받아오는 방법이 여러가지임
	 * 1) public String doWrite(HttpHttpServletRequest req) {
			String title = req.getParameter("title");
	 * 2) 커맨드 객체 방법 ( vo를 통해서 값을 받아옴 )
	 	public String doWrite(CommunityVO communityVO) {
			System.out.println(communityVO.getTitle());
			System.out.println(communityVO.getContents());
			System.out.println(communityVO.getNickname());
			System.out.println(communityVO.getAccount());
			System.out.println(communityVO.getWriteDate());
			return "";
		}
	 */
	//@PostMapping("/write")
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public ModelAndView doWrite
	(@ModelAttribute("writeForm") 
	@Valid CommunityVO communityVO, Errors errors, 
	HttpServletRequest request) {
		//String --> ModelAndView로 바꿈....
		//errors에는 vo에 작성한 message들이 들어있음 꼭 valid 뒤에 작성해야됨
		
		/*//로그인 상태 확인 --> interceptor로 처리
		if (session.getAttribute(Member.USER) == null) {
			return new ModelAndView("redirect:/login");
		}*/
		
		//필수 입력값에 값이 안들어가 있을 경우
		if(errors.hasErrors()) {
			ModelAndView view = new ModelAndView();
			view.setViewName("community/write");
			view.addObject("communityVO",communityVO);
			return view;
		}
		/*
		validation check on server area
		if(communityVO.getTitle() == null || communityVO.getTitle().length() == 0) {
			session.setAttribute("status", "emptyTitle");
			return "redirect:/write";
		}
		if(communityVO.getContents() == null || communityVO.getContents().length() == 0) {
			session.setAttribute("status", "emptyContents");
			return "redirect:/write";
		}
		if(communityVO.getWriteDate() == null || communityVO.getWriteDate().length() == 0) {
			session.setAttribute("status", "emptyDate");
			return "redirect:/write";
		}
		*/
		
		// 작성자의 IP를 얻어오는 코드
		String requestorIP = request.getRemoteAddr();
		communityVO.setRequestIP(requestorIP);
		
		communityVO.save();
		
		boolean isSuccess = communityService.createCommunity(communityVO);
		//실패와 성공을 구분하는 이유는? 성공하면 리스트로 실패면 라이트로 보내려고
		//성공이면!!
		if( isSuccess ) {	
			return new ModelAndView("redirect:/"); //이 url로 이동해라
		}//실패면!!
		/*session.setAttribute("status", "writeFail");*/
		return new ModelAndView("redirect:/write");
	}
	
	@RequestMapping("/read/{id}")
	public String viewReadPage(@PathVariable int id ) {
		
		//조회수 증가
		if ( communityService.incrementView(id)) {
			return "redirect:/view/"+id;
		}
		return "redirect:/";
		
	}
	
	
	@RequestMapping("/view/{id}")
	//데이터를 심어서 보여줌
	public ModelAndView viewViewPage(@PathVariable int id,  HttpSession session) {
			
		ModelAndView view = new ModelAndView();
		view.setViewName("community/view");

		CommunityVO viewOne = communityService.getOne(id);
		view.addObject("community",viewOne);		
		return view;
	}
	
	@RequestMapping("/recommend/{id}")
	//페이지만 보여줌
	public String doRecommend(@PathVariable int id,  HttpSession session) {
		boolean isSuccess = communityService.recommendCommunity(id);

		if(isSuccess) {
			return "redirect:/view/" + id;
			
		}
		return "redirect:/view/" + id;		
	}
	
	@RequestMapping("/get/{id}")
	public void download(@PathVariable int id, 
			HttpServletRequest request, HttpServletResponse response){
		CommunityVO community = communityService.getOne(id);
		String filename = community.getDisplayFilename();
		
		DownloadUtil download = new DownloadUtil("d:/uploadFiles/"+filename);
		try {
			download.download(request, response, filename);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	@RequestMapping("/delete/{id}")
	//데이터를 심어서 보여줌
	public String removePage(@PathVariable int id, HttpSession session) {
		
		MemberVO member = (MemberVO)session.getAttribute(Member.USER);
		
		CommunityVO community = communityService.getOne(id);
		
		boolean isMyCommunity = member.getAccount() == community.getAccount();
		
		if (isMyCommunity && communityService.removeOne(id)) {
			return "redirect:/";
		}
		
		return "/WEB-INF/view/error/404";
	}
	
	@RequestMapping(value="/modify/{id}" , method = RequestMethod.GET)
	public ModelAndView viewModifyPage(@PathVariable int id, HttpSession session) {
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		CommunityVO community = communityService.getOne(id);
		
		int account = member.getAccount();
		
		if( account != community.getAccount() ) {
			return new ModelAndView("error/404");
		}
		
		ModelAndView view = new ModelAndView();
		view.setViewName("community/write");
		view.addObject("communityVO", community);
		view.addObject("mode", "modify");
		
		return view;
		

	}
	
	@RequestMapping(value="/modify/{id}" , method = RequestMethod.POST)
	public String doModifyAction(@PathVariable int id, HttpSession session, HttpServletRequest request,
				@ModelAttribute("wrtieForm") @Valid CommunityVO communityVO, Errors errors) {
		
			MemberVO member = (MemberVO) session.getAttribute(Member.USER);
			CommunityVO originalVO = communityService.getOne(id);
			
			if(member.getAccount() != originalVO.getAccount()) {
				return "error/404";
			}
			if( errors.hasErrors()) {
				return "redirect:/modify/" + id;
			}
			
			CommunityVO newCommunity = new CommunityVO();
			newCommunity.setId(originalVO.getId());
			newCommunity.setAccount(member.getAccount());
			
			boolean isModify = false;
			
			//1. IP 변경확인
			
			String ip = request.getRemoteAddr();
			if( !ip.equals( originalVO.getRequestIP() ) ) {
				newCommunity.setRequestIP(ip);
				isModify = true;
			}
			
			// 2. 제목 변경 확인
			if ( !originalVO.getTitle().equals(communityVO.getTitle())) {
				newCommunity.setTitle(communityVO.getTitle());
				isModify = true;
			}
			
			// 3.내용 변경
			
			if( !originalVO.getContents().equals(communityVO.getContents())) {
				newCommunity.setContents(communityVO.getContents());
				isModify = true;
			}
			
			// 4.파일 변경
			if( communityVO.getDisplayFilename().length() > 0) {
				File file = new File("d:/uploadFiles/" + communityVO.getDisplayFilename());
				file.delete();
				communityVO.setDisplayFilename("");
			}
			else {
				communityVO.setDisplayFilename(originalVO.getDisplayFilename());
			}
			
			
			communityVO.save();
			if ( !originalVO.getDisplayFilename().equals(communityVO.getDisplayFilename())) {
				newCommunity.setDisplayFilename(communityVO.getDisplayFilename());
				isModify = true;
			}
			
			//5.변경 없는지 확인
			if ( isModify ) {
				communityService.updateCommunity(newCommunity);
				
			}
		
		return "redirect:/view/" + id;
		
	}
	
	
	
	
}


























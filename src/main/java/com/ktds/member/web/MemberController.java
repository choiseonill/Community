package com.ktds.member.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ktds.community.service.CommunityService;
import com.ktds.member.constants.Member;
import com.ktds.member.service.MemberService;
import com.ktds.member.vo.MemberVO;

@Controller
public class MemberController {

	private MemberService memberService;
	private CommunityService communityService;

	// set + ctrl space
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setCommunityService(CommunityService communityService) {
		this.communityService = communityService;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String viewLoginPage(HttpSession session) {
		if (session.getAttribute(Member.USER) != null) {
			return "redirect:/";
		}
		return "member/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String doLoginAction(@ModelAttribute("loginForm") @Valid MemberVO memberVO, Errors errors,
			HttpServletRequest req) {
		/*
		 * 필수 입력값에 값이 안들어가 있을 경우 if (errors.hasErrors()) { ModelAndView view = new
		 * ModelAndView(); view.setViewName("member/login"); view.addObject("memberVO",
		 * memberVO); return view; }
		 */
		HttpSession session = req.getSession();
		// FIXME :: 디비에 계정이 존재하지 않을 경우로 변경
		MemberVO loginMember = memberService.readMember(memberVO);
		if (loginMember != null) {
			session.setAttribute(Member.USER, loginMember);
			return "redirect:/";
		}
		return "redirect:/login";
		/*
		 * if (memberVO.getEmail().equals("admin") &&
		 * memberVO.getPassword().equals("1234")) { memberVO.setNickname("관리자");
		 * session.setAttribute(Member.USER, memberVO);
		 * session.removeAttribute("status"); return new ModelAndView("redirect:/"); }
		 * session.setAttribute("status", "fail"); return new
		 * ModelAndView("redirect:/login");
		 */
	}

	@RequestMapping("/logout")
	public String doLogoutAction(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	public String viewRegistPage() {
		return "member/regist";
	}

	@RequestMapping(value = "/regist", method = RequestMethod.POST)
	public ModelAndView doRegistAction(@ModelAttribute("registForm") @Valid MemberVO memberVO, Errors errors) {
		if (errors.hasErrors()) {
			/*
			 * ModelAndView view = new ModelAndView(); view.setviewname view.addobject
			 * return view 대신에 아래로 다할 수 있움!!! ModelAttribute 써서
			 */
			return new ModelAndView("redirect:member/regist");
		}
		if (memberService.createMember(memberVO)) {
			return new ModelAndView("redirect:/login");
		}
		return new ModelAndView("redirect:/regist");
	}

	@RequestMapping("/delete/process1")
	public String viewVerifyPage() {
		return "member/delete/process1";
	}

	@RequestMapping("/delete/process2")
	public ModelAndView viewDeleteMyCommunitiesPage(@RequestParam(required = false, defaultValue = "") String password,
			HttpSession session) {

		if (password.length() == 0) {
			return new ModelAndView("error/404");
		}

		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		member.setPassword(password);

		MemberVO verifyMember = memberService.readMember(member);

		if (verifyMember == null) {
			return new ModelAndView("redirect:/delete/process1");
		}

		int myCommunitiesCount = communityService.readMyCommunitiesCount(verifyMember.getAccount());

		ModelAndView view = new ModelAndView();
		view.setViewName("member/delete/process2");
		view.addObject("myCommunitiesCount", myCommunitiesCount);

		// 절대 중복이 날수 없는 난수를 만들어준다.
		String uuid = UUID.randomUUID().toString();
		session.setAttribute("__TOKEN__", uuid);
		view.addObject("token", uuid);

		return view;
	}

	@RequestMapping("/account/delete/{deleteFlag}")
	public String deleteAccount(HttpSession session, @RequestParam(required = false, defaultValue = "") String token,
			@PathVariable String deleteFlag) {

		String sessionToken = (String)session.getAttribute("__TOKEN__");
		if(sessionToken == null || !sessionToken.equals(token)) {
			return "error/404";
		}
		
		MemberVO member = (MemberVO) session.getAttribute(Member.USER);
		if (member == null) {
			return "redirect:/login";
		}

		int account = member.getAccount();

		if (memberService.removeAccount(account, deleteFlag)) {
			session.invalidate();
		}
		return "member/delete/delete";
	}

}

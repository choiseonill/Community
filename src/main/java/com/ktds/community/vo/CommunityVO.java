package com.ktds.community.vo;

import java.io.File;
import java.io.IOException;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import com.ktds.member.vo.MemberVO;

public class CommunityVO {

	private int id;
	// validation의 trigger는 controller에서 수행!!
	@NotEmpty(message = "제목은 필수 입력입니다.")
	private String title;
	@NotEmpty(message = "내용은 필수 입력입니다.")
	private String contents;
	/*@NotEmpty(message = "로그인이 필요합니다.")
	private String nickname;*/
	/*@NotEmpty(message = "로그인이 필요합니다.")*/
	private int account;
	/*@NotEmpty(message = "작성일을 선택해주세요.")*/
	private String writeDate;

	// 조회수
	private int viewCount;
	// 
	private String requestIP;
	// 추천하기
	private int recommendCount;
	
	private MultipartFile file;
	private String displayFilename;
	
	//작성자의 정보 :: JOIN
	private MemberVO memberVO;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	/*public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}*/

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public String getRequestIP() {
		return requestIP;
	}

	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	public int getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(int recommendCount) {
		this.recommendCount = recommendCount;
	}

	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public String getDisplayFilename() {
		if(displayFilename == null ) {
			displayFilename = "";
		}
		return displayFilename;
	}
	public void setDisplayFilename(String displayFilename) {
		this.displayFilename = displayFilename;
	}
	public String save() {
		//파일을 디스크에 쓰즌거
		if (file != null && !file.isEmpty()) {
			displayFilename = file.getOriginalFilename();
			File newFile = new File("d:/uploadFiles/"+file.getOriginalFilename());
			try {
				file.transferTo(newFile);
				return newFile.getAbsolutePath();
			} catch (IllegalStateException e) {
				throw new RuntimeException(e.getMessage(),e);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		return null;
	}

	public MemberVO getMemberVO() {
		return memberVO;
	}

	public void setMemberVO(MemberVO memberVO) {
		System.out.println("!111");
		System.out.println(memberVO);
		this.memberVO = memberVO;
	}
	
}

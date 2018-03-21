package com.ktds.member.vo;

import javax.validation.constraints.NotEmpty;

public class MemberVO {	
	
	//회원정보 수정할 때 주 키를 어떤 것으로 줄 지!!
	//가입한 회원의 수를 카운트 할려고 바꿈
	private int account;
	@NotEmpty(message = "닉네임 :: * 필수입력값!!")
	private String nickname;
	@NotEmpty(message = "이메일 :: * 필수입력값!!")
	private String email;
	@NotEmpty(message = "비밀번호 :: * 필수입력값!!")
	private String password;
	private String registDate;
	
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	
	
	
}

package com.ktds.member.dao;

import com.ktds.member.vo.MemberVO;

public interface MemberDAO {
	//회원가입한 사람 테이블에 넣을꺼어어어어
	public int insertMember(MemberVO memberVO);
	
	public MemberVO selectMember(MemberVO memberVO);
	
	public int deleteAccount(int account);
	

}	

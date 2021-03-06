package com.ktds.member.service;

import com.ktds.member.vo.MemberVO;

public interface MemberService {
	public boolean createMember(MemberVO member);
	public MemberVO readMember(MemberVO member);
	public boolean removeAccount(int account, String deleteFlag);

}

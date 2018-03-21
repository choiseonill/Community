package com.ktds.member.service;

import com.ktds.community.dao.CommunityDAO;
import com.ktds.member.dao.MemberDAO;
import com.ktds.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {

	private MemberDAO memberDao;
	private CommunityDAO communityDao;

	public void setCommunityDao(CommunityDAO communityDao) {
		this.communityDao = communityDao;
	}

	public void setMemberDao(MemberDAO memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public boolean createMember(MemberVO memberVO) {
		return memberDao.insertMember(memberVO) > 0;
	}

	@Override
	public MemberVO readMember(MemberVO memberVO) {
		return memberDao.selectMember(memberVO);
	}

	@Override
	public boolean removeAccount(int account, String deleteFlag) {
		if (deleteFlag.equals("y")) {
			communityDao.deleteMyCommunities(account);
		}
		return memberDao.deleteAccount(account) > 0;
	}

}

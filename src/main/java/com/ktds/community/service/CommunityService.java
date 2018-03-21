package com.ktds.community.service;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityService {
	
	public List<CommunityVO> getAll();
	//dao를 호출하기 위한 징검다리를 만듦 : true false로
	public boolean createCommunity(CommunityVO communityVO);
	
	public CommunityVO getOne(int id);
	
	public int readMyCommunitiesCount(int account);
	
	public List<CommunityVO> readMyCommunities(int account);
	
	public boolean recommendCommunity(int id);
	//조회수 증가
	public boolean incrementView(int id);
	//삭제
	public boolean removeOne(int id);
	
	public boolean deleteCommunities(List<Integer> ids, int account);
	
	public boolean updateCommunity(CommunityVO communityVO);
	

	
}

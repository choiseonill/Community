package com.ktds.community.dao;

import java.util.List;

import com.ktds.community.vo.CommunityVO;

public interface CommunityDAO {
	
	//리스트 
	public List<CommunityVO> selectAll();
	
	public int insertCommunity(CommunityVO communityVO);
	//아이디로 게시글 하나만 가져오는 거
	public CommunityVO selectOne(int id);
	
	public int selectMyCommunitiesCount(int account);
	
	public List<CommunityVO> selectMyCommunities(int ACCOUNT);
	
	//C.U.D --> INT
	public int incrementViewCount(int id);
	//추천하기
	public int incrementRecommendCount(int id);
	//게시판삭제하기
	public int deleteOne(int id);
	
	public int deleteCommunities(List<Integer> ids, int account);
	 
	public int deleteMyCommunities(int id);
	
	public int updateCommunity(CommunityVO communityVO);
	
	
	
	

}


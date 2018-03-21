package com.ktds.community.service;

import java.util.ArrayList;
import java.util.List;

import com.ktds.community.dao.CommunityDAO;
import com.ktds.community.vo.CommunityVO;

public class CommunityServiceImpl implements CommunityService{
	
	private CommunityDAO communityDao;	
	
	public void setCommunityDao(CommunityDAO communityDao) {
		this.communityDao = communityDao;
	}	
	@Override
	public List<CommunityVO> getAll() {
		return communityDao.selectAll();
	}
	@Override
	public boolean createCommunity(CommunityVO communityVO) {		
		String contents = communityVO.getContents();
		// \n --> <br/>
		contents = contents.replace("\n", "<br/>");
		contents = filter(contents);
		communityVO.setContents(contents);
		
		/*//금지어가 존재하면 리턴 false; --> 필터가 boolean형일 때
		if ( filter(contents) || filter(communityVO.getTitle())) {
			// || shortcut 평가
			return false;
		}*/
		
		int insertCount = communityDao.insertCommunity(communityVO);
		//한 개 이상을 등록했는가 확인
		return insertCount > 0;
	}

	@Override
	public CommunityVO getOne(int id) {	
		//조회수 증가가 이루어지면
		//if(communityDao.incrementViewCount(id) > 0) {
			return communityDao.selectOne(id);
		/*}
		return null;*/	
		
	}
	//추천 수 확인
	@Override
	public boolean recommendCommunity(int id) {
		int recommendCount = communityDao.incrementRecommendCount(id);
		return recommendCount > 0;
	}
	@Override
	public boolean incrementView(int id) {
		return communityDao.incrementViewCount(id)>0;
	}

	//금지어
	public String filter(String str) {
		List<String> blackList = new ArrayList<String>();
		blackList.add("욕");
		blackList.add("시");
		
		//str ==> 시밤 바야
		//공백을 기준으로 자름
		//String[] splitString = str.split(" ");
		//for (String word : splitString) {
			//return blackList.contains(word);
			for (String blackString : blackList) {
				/*if( word.contains(blackString)) {
					return true;*/
				str = str.replace(blackString, "뿅뿅");
				}
		//}						
		return str;
	}
	@Override
	public boolean removeOne(int id) {
		int remove = communityDao.deleteOne(id);
		return remove >= 0;
	}
	@Override
	public boolean updateCommunity(CommunityVO communityVO) {
		return communityDao.updateCommunity(communityVO) > 0 ;
	}
	@Override
	public int readMyCommunitiesCount(int account) {
		return communityDao.selectMyCommunitiesCount(account);
	}
	@Override
	public List<CommunityVO> readMyCommunities(int account) {
		return communityDao.selectMyCommunities(account);
	}
	@Override
	public boolean deleteCommunities(List<Integer> ids, int account) {
		return communityDao.deleteCommunities(ids, account) > 0;
	}
}
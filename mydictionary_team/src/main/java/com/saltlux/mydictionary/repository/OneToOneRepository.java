package com.saltlux.mydictionary.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.saltlux.mydictionary.vo.OneToOneVo;

@Repository
public class OneToOneRepository {
	
	@Autowired
	private SqlSession sqlSession;
	
	public List<OneToOneVo> findAll(int page, String name) {
		int start = 1 + (page-1) * 10; //1, 11, 21, 31
		int end = page * 10; //10, 20, 30, 40
		Map params = new HashMap();
		params.put("start", start);
		params.put("end", end);
		params.put("name", name);
		

		return sqlSession.selectList("onetoone.findAll", params);
	}
	
	public List<OneToOneVo> findAll(int page) {
		int start = 1 + (page-1) * 10; //1, 11, 21, 31
		int end = page * 10; //10, 20, 30, 40
		Map params = new HashMap();
		params.put("start", start);
		params.put("end", end);
		

		return sqlSession.selectList("onetoone.adminFindAll", params);
	}


	public List<OneToOneVo> search(String searchOption, String keyword, int page) {
		int start = 1 + (page-1) * 10; //1, 11, 21, 31
		int end = page * 10; //10, 20, 30, 40
		Map params = new HashMap();
		params.put("start", start);
		params.put("end", end);
		params.put("keyword", keyword);
		
		if(searchOption.equals("titleContent")) {
			return sqlSession.selectList("onetoone.searchTitleContent", params);
		}
		else
			return sqlSession.selectList("onetoone.searchTitle", params);
		
	}

	public OneToOneVo findOne(String no) {
		return sqlSession.selectOne("onetoone.findOne", no);
	}

	public boolean write(OneToOneVo vo) {
		int result = sqlSession.insert("onetoone.write", vo);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}

	public boolean update(String no, String title, String content) {
		Map params = new HashMap();
		params.put("no", no);
		params.put("title", title);
		params.put("content", content);
		int result = sqlSession.update("onetoone.update", params);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
		
	}

	public int findAllCnt(String nickname) {
		return sqlSession.selectOne("onetoone.findAllCnt", nickname);
	}

	public boolean updateReply(String no, String reply) {
		Map params = new HashMap();
		params.put("no", no);
		params.put("reply", reply);
		int result = sqlSession.update("onetoone.insertReply", params);
		if(result == 1) {
			return true;
		}else {
			return false;
		}
	}
}
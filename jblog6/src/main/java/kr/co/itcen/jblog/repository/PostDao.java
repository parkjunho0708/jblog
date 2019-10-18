package kr.co.itcen.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.PostVo;

@Repository
public class PostDao {
	
	@Autowired
	private SqlSession sqlSession;

	// 포스트 데이터 삽입
	public void adminPostInsert(PostVo postVo) {
		sqlSession.insert("post.adminPostInsert", postVo);
	}
	
	// 메인 페이지에 포스트 데이터 출력
	public List<PostVo> mainPostList(String userId) {
		return sqlSession.selectList("blog.mainPost", userId);
	}
	
	public List<PostVo> categoryPost(Integer categoryNo) {
		return sqlSession.selectList("blog.categoryPost", categoryNo);
	}
	
	public PostVo getPost(Integer categoryNo, Integer postNo) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("cateNo", categoryNo);
		map.put("postNo", postNo);
		return sqlSession.selectOne("blog.getPost", map);
	}

	public PostVo getPostInfo(int postNo) {
		return sqlSession.selectOne("post.getPostInfo", postNo);
	}

	public void increaseSpecifiedPostCount(int categoryNo) {
		sqlSession.update("post.increasePostCount", categoryNo);
	}

	public void adminPostDelete(int postNo) {
		sqlSession.delete("post.deleteSpecifiedPost", postNo);
	}

	public void decreasePostCountDeletedOne(int categoryNo) {
		sqlSession.update("post.decreasePostCount", categoryNo);
	}

}

package kr.co.itcen.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.PostVo;

@Repository
public class BlogDao {

	@Autowired
	private SqlSession sqlSession;

	// 사용자가 회원가입을 했을 때, 사용자의 블로그를 자동으로 생성해주는 처리
	public void createMyBlog(String userId, String blogTitle, String blogLogo) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("blogTitle", blogTitle);
		map.put("blogLogo", blogLogo);
		
		sqlSession.insert("blog.createMyBlog", map);
	}

	// 블로그 아이디(userId = blogId)를 통해 해당 유저의
	// blogTitle, blogLogo의 값을 가지고 옴.
	public BlogVo get(String userId) {
		BlogVo result = sqlSession.selectOne("blog.getByUserId", userId);
		return result;
	}

	public void blogAdminWrite(BlogVo blogVo) {
		sqlSession.update("blog.blogAdminWrite", blogVo);
	}

	public void adminCategoryAdd(String categoryname, String categorydesc, String userid) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("categoryname", categoryname);
		map.put("categorydesc", categorydesc);
		map.put("userid", userid);
		sqlSession.insert("blog.adminCategoryAdd", map);
	}

	public List<PostVo> blogMainPostList(String userId) {
		List<PostVo> list = sqlSession.selectList("post.getblogMainPostList", userId);
		return list;
	}

}

package kr.co.itcen.jblog.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.jblog.repository.BlogDao;
import kr.co.itcen.jblog.repository.CategoryDao;
import kr.co.itcen.jblog.repository.PostDao;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.PostVo;

@Service
public class BlogService {
	
	@Autowired
	private BlogDao blogDao;
	
	@Autowired
	private PostDao postDao;
	
	@Autowired
	private CategoryDao categoryDao;

	// 사용자가 회원가입을 했을 때, 사용자의 블로그를 자동으로 생성해주는 처리
	public void createMyBlog(String userId, String blogTitle, String blogLogo) {
		blogDao.createMyBlog(userId, blogTitle, blogLogo);
	}

	// 블로그 아이디(userId = blogId)를 통해 해당 유저의
	// blogTitle, blogLogo의 값을 가지고 옴.
	public BlogVo get(String userId) {
		return blogDao.get(userId);
	}

	// blog-admin-basic.jsp 에서 정보수정한 데이터 전달
	public void blogAdminWrite(BlogVo blogVo) {
		blogDao.blogAdminWrite(blogVo);
	}
	
	public Map<String, Object> getBlogInfomation(String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blogVo", blogDao.get(userId)); // 블로그 데이터 가져오기
		map.put("categoryList", categoryDao.getCategoryList(userId)); // 카테고리 리스트로 가져오기
		return map;
	}
	
	public Map<String,Object> getCategoryPost(Integer categoryNo, Integer postNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPost", postDao.getPost(categoryNo, postNo));
		map.put("postList", postDao.categoryPost(categoryNo));
		return map;
	}

	public List<PostVo> blogMainPostList(String userId) {
		return blogDao.blogMainPostList(userId);
	}

	public List<PostVo> categoryPost(Integer categoryNo) {
		return postDao.categoryPost(categoryNo);
	}
	
}

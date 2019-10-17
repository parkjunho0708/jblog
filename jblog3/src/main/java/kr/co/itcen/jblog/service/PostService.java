package kr.co.itcen.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.jblog.repository.PostDao;
import kr.co.itcen.jblog.vo.PostVo;

@Service
public class PostService {

	@Autowired
	private PostDao postDao;

	public void adminPostInsert(PostVo postVo) {
		postDao.adminPostInsert(postVo);
	}

	public PostVo getPostInfo(int postNo) {
		return postDao.getPostInfo(postNo);
	}
	
}

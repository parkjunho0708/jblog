package kr.co.itcen.jblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.jblog.repository.UserDao;
import kr.co.itcen.jblog.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public UserVo getUser(UserVo vo) {
		return userDao.get(vo);
	}

	public void join(UserVo vo) {
		System.out.println("userDao : " + userDao);
		userDao.insert(vo);
	}

	public Boolean existUser(String userId) {
		return userDao.get(userId) != null;
	}

}

package kr.co.itcen.jblog.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.exception.UserDaoException;
import kr.co.itcen.jblog.vo.UserVo;

@Repository
public class UserDao {

	@Autowired
	private SqlSession sqlSession;

	public Boolean insert(UserVo vo) throws UserDaoException {
		System.out.println("sqlSession : " + sqlSession);
		int count = sqlSession.insert("user.insert", vo);
		return count == 1;
	}

	// 아이디 유효성 검사
	public UserVo get(String userId) {
		UserVo result = sqlSession.selectOne("user.getByUserId", userId);
		return result;
	}
	
	public UserVo get(UserVo vo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", vo.getUserId());
		map.put("userPassword", vo.getUserPassword());
		UserVo result = sqlSession.selectOne("user.getByIdAndPassword", map);
		return result;
	}

}

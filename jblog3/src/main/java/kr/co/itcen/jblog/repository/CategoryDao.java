package kr.co.itcen.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.jblog.vo.CategoryVo;

@Repository
public class CategoryDao {

	@Autowired
	private SqlSession sqlSession;

	// blog-admin-category.jsp 에서 추가한 데이터 전달
	public void adminCategoryAdd(CategoryVo categoryVo) {
		sqlSession.insert("category.adminCategoryAdd", categoryVo);
	}

	public List<CategoryVo> adminCategorySelect(String userId) {
		List<CategoryVo> result = sqlSession.selectList("category.getAllCategoryList", userId);
		return result;
	}

	public CategoryVo adminCategoryGetRecentData(String userId) {
		return sqlSession.selectOne("category.adminCategoryGetRecentData", userId);
	}

	public void adminCategoryDelete(int categoryNo, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("categoryNo", categoryNo);
		sqlSession.delete("category.adminCategoryDelete", map);
	}

	// 카테고리 리스트로 가져오기
	public List<CategoryVo> adminCategoryMatchedUserId(String userId) {
		List<CategoryVo> list = sqlSession.selectList("category.getAllCategoryName", userId);
		return list;
	}

	public CategoryVo getCategoryInformation(int categoryNo) {
		return sqlSession.selectOne("category.getCategoryInfo", categoryNo);
	}

	// 카테고리 상위 7개 출력
	public List<CategoryVo> getCategoryList(String userId) {
		List<CategoryVo> list = sqlSession.selectList("category.getCategoryListTopSeven", userId);
		return list;
	}

}

package kr.co.itcen.jblog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.itcen.jblog.dto.JSONResult;
import kr.co.itcen.jblog.service.CategoryService;
import kr.co.itcen.jblog.vo.CategoryVo;

@RestController("categoryApiController")
@RequestMapping("/api/category")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ResponseBody
	@RequestMapping("/categoryinsert")
	public String adminCategoryInsert(
			@RequestBody CategoryVo categoryVo,
			Model model) throws JsonProcessingException {
		categoryService.adminCategoryAdd(categoryVo); 
		CategoryVo vo = categoryService.adminCategoryGetRecentData(categoryVo.getUserId());
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonInString = objectMapper.writeValueAsString(vo);
        return jsonInString;
	}
	
	@ResponseBody
	@RequestMapping("/categorydelete")
	public JSONResult adminCategoryDelete(
			@RequestParam int categoryNo,
			@RequestParam String userId,			
			Model model) {
		// 카테고리에 연결된 포스트들 삭제 후, 카테고리 삭제
		categoryService.adminPostDeleteWhenCategoryDelete(categoryNo);
		System.out.println("categoryNo1 : " + categoryNo);
		categoryService.adminCategoryDelete(categoryNo, userId);
		System.out.println("categoryNo2 : " + categoryNo);
		System.out.println("categoryNo3 : " + categoryNo);
		System.out.println("categoryNo4 : " + categoryNo);
		return JSONResult.success(categoryNo);
	}
}

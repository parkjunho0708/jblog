package kr.co.itcen.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.service.CategoryService;
import kr.co.itcen.jblog.service.FileUploadService;
import kr.co.itcen.jblog.service.PostService;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;

@Controller
@RequestMapping("/{userId:(?!assets).*}") // asset을 아이디로 생각하지 않게 하기 위한 처리
public class BlogController {

	private static final Log Log = LogFactory.getLog(BlogController.class);

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	FileUploadService fileUploadService;

	// 블로그 메인
	@RequestMapping(value = { "", "/{pathNo1}", "/{pathNo1}/{pathNo2}" })
	public String index(
			@PathVariable(value = "userId") String userId,
			@PathVariable(value = "pathNo1") Optional<Integer> pathNo1,
			@PathVariable(value = "pathNo2") Optional<Integer> pathNo2, 
			Model model) {
		System.out.println("userId : " + userId);
		System.out.println("pathNo1 : " + pathNo1);
		Integer categoryNo = 0;
		Integer postNo = 0;

		Map<String, Object> map = new HashMap<String, Object>(); 
			
		if (pathNo1.isPresent()) { // 카테고리만
			categoryNo = pathNo1.get();
			map.put("postList", blogService.categoryPost(categoryNo));
			System.out.println("pathNo1 postList : " + map);
		} else if (pathNo2.isPresent()) { // 카테고리의 글
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
			map.putAll(blogService.getCategoryPost(categoryNo, postNo));
			System.out.println("pathNo2 postList : " + map);
		} else {
			map.put("postList", blogService.blogMainPostList(userId));
			System.out.println("postList : " + map);
		}

		map.putAll(blogService.getBlogInfomation(userId));
		System.out.println("blogService postList : " + map);

		if (map.get("blogVo") == null) {
			return "error/404";
		}

		model.addAllAttributes(map);

		return "blog/blog-main";
	}

	// 블로그 관리 페이지
	@RequestMapping(value = {"/admin/basic"})
	public String adminBasic(
			@PathVariable("userId") String userId,
			Model model) {
		BlogVo blogVo = blogService.get(userId);
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	// 블로그 정보 수정
	@RequestMapping(value = {"/admin/basic/update"}, method = RequestMethod.POST)
	public String adminBasicWrite(
			@PathVariable("userId") String userId,
			@ModelAttribute BlogVo blogVo,
			@RequestParam(value="logo-file", required = false) MultipartFile multipartFile,
			Model model) {
		
		if(!multipartFile.isEmpty()) {
			String saveFileName = fileUploadService.restore(multipartFile);
			blogVo.setBlogLogo(saveFileName);			
		} 
		
		blogVo.setUserId(userId);
		blogService.blogAdminWrite(blogVo); // 기본설정 수정
		model.addAttribute("blogVo", blogVo);
		return "redirect:/" + userId;
	}
	
	// 카테고리 관리 페이지
	@RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
	public String adminCategory(@PathVariable("userId") String userId, Model model) {
		List<CategoryVo> list = categoryService.adminCategorySelect(userId);
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	// 블로그 포스트 글 작성 페이지
	@RequestMapping(value = {"/admin/write"}, method = RequestMethod.GET)
	public String adminWrite(
			@PathVariable("userId") String userId,
			Model model) {
		List<CategoryVo> list = categoryService.adminCategoryMatchedUserId(userId);
		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}
	
	// 블로그 포스트 글 작성 및 추가
	@RequestMapping(value = {"/admin/post/update"}, method = RequestMethod.POST)
	public String adminPostWrite(
			@ModelAttribute PostVo postVo,
			@PathVariable("userId") String userId,
			Model model) {
		System.out.println("userId : " + userId);
		postService.adminPostInsert(postVo);
		return "redirect:/" + userId;
	}
	
	// 포스트 상세보기
	@RequestMapping(value = { "/{postNo}/postInfo" })
	public String getPostInfo(
			@PathVariable("postNo") int postNo, 
			Model model) {
		PostVo postVo = postService.getPostInfo(postNo);
		model.addAttribute("postVo", postVo);
		return "post/blog-post-Info";
	}
	
	// 카테고리 상세보기
	@RequestMapping(value = { "/{categoryNo}/categoryInfo" })
	public String getCategoryInfo(
			@PathVariable("categoryNo") int categoryNo, 
			Model model) {
		CategoryVo categoryVo = categoryService.getCategoryInformation(categoryNo);
		model.addAttribute("categoryVo", categoryVo);
		return "category/blog-category-Info";
	}
}

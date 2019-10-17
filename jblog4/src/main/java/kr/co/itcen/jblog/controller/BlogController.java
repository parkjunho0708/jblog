package kr.co.itcen.jblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

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

import kr.co.itcen.jblog.security.Auth;
import kr.co.itcen.jblog.security.AuthUser;
import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.service.CategoryService;
import kr.co.itcen.jblog.service.FileUploadService;
import kr.co.itcen.jblog.service.PostService;
import kr.co.itcen.jblog.vo.BlogVo;
import kr.co.itcen.jblog.vo.CategoryVo;
import kr.co.itcen.jblog.vo.PostVo;
import kr.co.itcen.jblog.vo.UserVo;

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
			
		if (pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
			map.put("postList", blogService.categoryPost(categoryNo));
		} else if (pathNo2.isPresent()) {
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
			map.putAll(blogService.getCategoryPost(categoryNo, postNo));
		} else {
			map.put("postList", blogService.blogMainPostList(userId));
		}

		map.putAll(blogService.getBlogInfomation(userId));

		if (map.get("blogVo") == null) {
			return "error/404";
		}

		model.addAllAttributes(map);

		return "blog/blog-main";
	}

	// 블로그 관리 페이지
	@Auth
	@RequestMapping(value = {"/admin/basic"})
	public String adminBasic(
			@AuthUser UserVo authUser,
			@PathVariable("userId") String userId,
			Model model) {
		if(!userId.equals(authUser.getUserId())){
			return "redirect:/" + userId;
		}
		
		BlogVo blogVo = blogService.get(userId);
		
		if (blogVo == null) {
			return "error/404";
		}
		
		model.addAttribute("blogVo", blogVo);
		return "blog/blog-admin-basic";
	}
	
	// 블로그 정보 수정
	@Auth
	@RequestMapping(value = {"/admin/basic/update"}, method = RequestMethod.POST)
	public String adminBasicWrite(
			@AuthUser UserVo authUser,
			@PathVariable("userId") String userId,
			@ModelAttribute @Valid BlogVo blogVo,
			@RequestParam(value="logo-file", required = false) MultipartFile multipartFile,
			Model model) {
		if(!userId.equals(authUser.getUserId())){
			return "redirect:/" + userId;
		}
		
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
	@Auth
	@RequestMapping(value = {"/admin/category"}, method = RequestMethod.GET)
	public String adminCategory(
			@AuthUser UserVo authUser,
			@PathVariable("userId") String userId, 
			Model model) {
		if(!userId.equals(authUser.getUserId())){
			return "redirect:/" + userId;
		}
		
		List<CategoryVo> list = categoryService.adminCategorySelect(userId);
		model.addAttribute("list", list);
		return "blog/blog-admin-category";
	}
	
	// 블로그 포스트 글 작성 페이지
	@Auth
	@RequestMapping(value = {"/admin/write"}, method = RequestMethod.GET)
	public String adminWrite(
			@AuthUser UserVo authUser,
			@PathVariable("userId") String userId,
			Model model) {
		if(!userId.equals(authUser.getUserId())){
			return "redirect:/" + userId;
		}
		
		List<CategoryVo> list = categoryService.adminCategoryMatchedUserId(userId);
		
		if (list == null) {
			return "error/404";
		}
		
		model.addAttribute("list", list);
		return "blog/blog-admin-write";
	}
	
	// 블로그 포스트 글 작성 및 추가
	@Auth
	@RequestMapping(value = {"/admin/post/update"}, method = RequestMethod.POST)
	public String adminPostWrite(
			@AuthUser UserVo authUser,
			@ModelAttribute PostVo postVo,
			@PathVariable("userId") String userId,
			Model model) {
		if(!userId.equals(authUser.getUserId())){
			return "redirect:/" + userId;
		}
		
		postService.adminPostInsert(postVo); // 포스트글 등록
		
		if (postVo == null) {
			return "error/404";
		}
		
		postService.increaseSpecifiedPostCount(postVo.getCategoryNo()); // 포스트에 등록한 카테고리 증가
		return "redirect:/" + userId;
	}
	
	// 포스트 상세보기
	@RequestMapping(value = { "/{postNo}/postInfo" })
	public String getPostInfo(
			@PathVariable("postNo") int postNo, 
			Model model) {
		
		PostVo postVo = postService.getPostInfo(postNo);
		System.out.println("postVo : " + postVo);
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
	
	// 포스트 글 삭제하기
	@Auth
	@RequestMapping(value = { "/{postNo}/{categoryNo}/admin/post/delete" })
	public String deletePost(
			@AuthUser UserVo authUser,
			@PathVariable("userId") String userId,
			@PathVariable("postNo") int postNo,
			@PathVariable("categoryNo") int categoryNo,
			Model model) {
		if(!userId.equals(authUser.getUserId())){
			return "redirect:/" + userId;
		}
		
		postService.adminPostDelete(postNo);
		postService.decreasePostCountDeletedOne(categoryNo);
		return "redirect:/" + userId;
	}
}

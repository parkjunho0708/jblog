package kr.co.itcen.jblog.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.jblog.service.BlogService;
import kr.co.itcen.jblog.service.UserService;
import kr.co.itcen.jblog.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BlogService blogService;

	// 회원가입이 정상적으로 완료되었을 때, 출력되는 페이지
	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	// 회원가입 페이지로 이동
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

	// 회원가입을 하고 회원정보 및 회원의 개인블로그 생성
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(
			@ModelAttribute @Valid UserVo vo, 
			BindingResult result, 
			Model model) {
		if (result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/join";
		}

		// 사용자의 회원정보 추가
		userService.join(vo);

		// 사용자의 블로그 생성
		blogService.createMyBlog(vo.getUserId(), vo.getUserName() + "의 블로그입니다.", "spring-logo.jpg");

		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

}
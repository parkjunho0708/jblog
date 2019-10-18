package kr.co.itcen.jblog.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

	private static final Log Log = LogFactory.getLog(MainController.class);

	@RequestMapping({ "", "/main" })
	public String index() {
		return "main/index";
	}

}

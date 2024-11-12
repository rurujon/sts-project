package com.exe.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@RequestMapping("/board")
	@ResponseBody
	public String index() {
		
		return "안녕하세요 환영합니다.";
	}

	@RequestMapping("/")
	public String home() {
		
		return "redirect:/question/list";
	}
}

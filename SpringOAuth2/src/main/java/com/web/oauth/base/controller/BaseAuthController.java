package com.web.oauth.base.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.web.oauth.base.dto.SessionUser;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BaseAuthController {
	
	private final HttpSession httpSession;
	
	@GetMapping("/")
	public String index(Model model) {
		
		SessionUser user = (SessionUser)httpSession.getAttribute("user");
		
		if(user!=null) {
			model.addAttribute("email",user.getEmail());
			model.addAttribute("name",user.getName());
			model.addAttribute("picture",user.getPicture());
		}
		
		return "index";
		
	}
	
	

}

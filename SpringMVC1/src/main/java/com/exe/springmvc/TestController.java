package com.exe.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("test.controller")
public class TestController {
	/*
	@RequestMapping(value = "/test/param.action", method = RequestMethod.GET)
	public String processGetRequest() {
		
		System.out.println("get 방식 Request");
		
		return "paramResult";
		
		
	}
	
	@RequestMapping(value = "/test/param.action", method = RequestMethod.POST)
	public String processPostRequest() {
		
		System.out.println("Post 방식 Request");
		
		return "paramResult";
		
		
	}*/
	
	
	
	@RequestMapping(value = "/test/param.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String processRequest(PersonDTO dto, String name, HttpServletRequest req) {
		
		System.out.println("get/post 방식");
		
		System.out.println(name);
		System.out.println(req.getParameter("phone"));
		
		System.out.println(dto);	//hashcode
		System.out.println(dto.getName());
		System.out.println(dto.getEmail());
		System.out.println(dto.getPhone());
		
		return "paramResult";
	}

	
	@RequestMapping(value = "/test/mav.action",
			method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView mavRequest(PersonDTO dto) {
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("dto",dto);	//model
		mav.setViewName("paramResult");	//view
		
		return mav;
	}
	
	
	@RequestMapping(value = "/test/redirect.action",
			method = {RequestMethod.GET,RequestMethod.POST})
	public String mavRedirectRequest() {
		
		return "redirect:/";
		
		//return "redirect:/hello.action";
		
		//return "hello";
	}

}

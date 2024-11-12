package com.spring.react;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@GetMapping("/api/hello")
	public List hello() {
		return Arrays.asList("Spring","리액트 프로젝트");
	}

}

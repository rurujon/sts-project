package com.exe.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAfterAdvice {
	
	@After("execution(public void com.exe.aop.*.*(..))")
	public void afterMethodCall() {
		System.out.println("메소드 실행 후(afterAdvice)");
		
	}

}

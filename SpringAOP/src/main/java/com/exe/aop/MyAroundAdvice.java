package com.exe.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyAroundAdvice {
	
	
	@Around("execution(public void com.exe.aop.*.*(..))")
	public Object aroundMethodCall(ProceedingJoinPoint joinPoint) {
		
		Object result = null;
		
		try {
			
			System.out.println("메소드 실행 전(AroundBefore)");
			result = joinPoint.proceed();
			System.out.println("메소드 실행 후(AroundAfter)");
			
			
		} catch (Throwable e) {
			// TODO: handle exception
		}
		
		return result;
		
	}

}

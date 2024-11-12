package com.exe.springdi3;

import org.springframework.context.support.GenericXmlApplicationContext;

public class MessageService {
	
	public void messageService() {
		//BeanFactory 생성
		GenericXmlApplicationContext context =
				new GenericXmlApplicationContext("app-context.xml");
		
		//Bean 객체를 요청
		Message ms = (Message)context.getBean("message");
		ms.sayHello("배수지");
		
		
	}

}

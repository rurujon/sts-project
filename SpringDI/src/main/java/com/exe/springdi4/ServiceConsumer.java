package com.exe.springdi4;

import org.springframework.context.support.GenericXmlApplicationContext;

public class ServiceConsumer {
	
	MessageService ms;
	TimeService ts;
	JobService js;
	
	//생성자 의존성 주입(Dependency Injection)
	public ServiceConsumer(MessageService ms) {
		this.ms = ms;
	}
	
	//메소드 의존성 주입
	public void setTimeService(TimeService ts) {
		this.ts = ts;
	}
	
	public void setJobService(JobService js) {
		this.js = js;
	}

	public void consumerService() {
		
		//GenericXmlApplicationContext context =
		//	new GenericXmlApplicationContext("app-context.xml");
	
		//MessageService ms = (MessageService)context.getBean("messageService");
	
		String message = ms.getMessage();
		System.out.println(message);
		
		
		String time = ts.getTimeString();
		System.out.println(time);
		
		js.getJob();
	
	}
	
}

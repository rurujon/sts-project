package com.exe.aop;

import org.springframework.context.support.GenericXmlApplicationContext;

public class AopMain {

	public static void main(String[] args) {
		
		GenericXmlApplicationContext context =
				new GenericXmlApplicationContext("app-context.xml");
		
		TargetA ta = (TargetA)context.getBean("targetA");
		
		ta.doAnother1(10);
		ta.doAnother2();
		ta.doSomething1();
		ta.doSomething2(10,"a");
		
		/*
		TargetB tb = (TargetB)context.getBean("targetB");
		
		tb.doAnother1();
		tb.doAnother2();
		tb.doSomething1();
		tb.doSomething2();
		*/
	}

}

package com.exe.springdi2;

public class MessageCall {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("1. 일반적인 객체생성..");
		
		MessageEn ob1 = new MessageEn();
		ob1.sayHello("suzi");
		
		MessageKr ob2 = new MessageKr();
		ob2.sayHello("수지");

		
		System.out.println("2. 인터페이스 객체생성..");
		
		Message ms = null;
		
		ms = new MessageEn();
		ms.sayHello("Miss A");
		
		ms = new MessageKr();
		ms.sayHello("미스A");
		
		
		

	}

}

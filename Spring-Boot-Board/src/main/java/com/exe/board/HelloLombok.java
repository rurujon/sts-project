package com.exe.board;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//RequiredArgsConstructor : 오버로딩된 생성자를 만듬
//단 이럴 경우 변수를 반드시 final로 줘야 한다. lombok의 약속이다.
@RequiredArgsConstructor
@Getter
@Setter
public class HelloLombok {
	
	private final String name;
	private final int age;
	/*
	public static void main(String[] args) {
		
		HelloLombok hk = new HelloLombok("고윤정", 35);
		
		//hk.setName("배수지");
		//hk.setAge(29);
		
		System.out.println(hk.getName());
		System.out.println(hk.getAge());
		
	}*/

}

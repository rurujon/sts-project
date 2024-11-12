package com.exe.board.user;

import lombok.Getter;

@Getter
public enum UserRole {
	
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	//ROLE_ : 문법
	//로그인하는 사람이 admin인지 일반유저인지 구분위함
	
	private String value;
	
	UserRole(String value){
		this.value = value;
	}

}

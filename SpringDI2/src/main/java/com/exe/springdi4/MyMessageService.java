package com.exe.springdi4;

import org.springframework.stereotype.Component;

@Component("messageService")
public class MyMessageService implements MessageService{
	
	public String getMessage() {
		
		return "안녕 방가방가...";
	}

}

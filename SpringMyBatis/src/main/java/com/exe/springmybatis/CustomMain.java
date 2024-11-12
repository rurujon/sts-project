package com.exe.springmybatis;

import java.util.List;

import org.springframework.context.support.GenericXmlApplicationContext;

public class CustomMain {
	
	public static void main(String[] args) {
		
		GenericXmlApplicationContext context =
				new GenericXmlApplicationContext("app-context.xml");
		
		CustomDAO dao = (CustomDAO)context.getBean("customDAO");
		
		CustomDTO dto;
		
		//insert
		/*
		dto = new CustomDTO();
		dto.setId(444);
		dto.setName("정인선");
		dto.setAge(27);
		
		dao.insertData(dto);
		
		System.out.println("insert 완료");
		*/
		
		//update
		/*
		dto = new CustomDTO();
		dto.setId(111);
		dto.setName("박민영");
		dto.setAge(39);
		
		dao.updateData(dto);
		System.out.println("update 완료");
		*/
		
		//delete
		/*
		dao.deleteData(444);
		System.out.println("delete 완료.");
		*/
		
		//oneSelect
		dto=dao.getReadData(222);
		
		if(dto!=null) {
			System.out.printf("%d %s %d\n", dto.getId(), dto.getName(), dto.getAge());
		}
		System.out.println("oneSelect 완료.");
		
		//select
		List<CustomDTO> lists = dao.getLists();
		
		for(CustomDTO vo : lists) {
			System.out.printf("%d %s %d\n", vo.getId(), vo.getName(), vo.getAge());
		}
		
		System.out.println("select 완료");
		
	}

}

package com.exe.board;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@RestController : @Controller + @responseBody(반환값이 문자)

@Controller
//@RestController
public class HelloController {
	
	//부트에서는 기본적으로 컨트롤러를 사용할 수 없다.
	//그럼에도 사용하고 싶다면 RequestMapping 아래에 ResponseBody를 추가해준다.
	//그게 아니라면 RestController를 사용한다.
	
	//Rest 형식 : .action 같은 확장자를 붙이지 않고 실행하는 것.
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		
		//return "bbs/list";
		return "Hello Spring Boot!!!";
	}

}


/*

[JPA(Java Persistence API)]

JPA ORM을 이용하면 개발자가 쿼리를 직접 작성하지 않아도 데이터베이스의 데이터를 처리
JPA는 인터페이스이다. 따라서 인터페이스를 구현하는 실제 클래스가 필요하다


insert into question (subject, content) values ('안녕!', '가입 인사드립니다');
insert into question (subject, content) values ('질문!', 'SpringBoot가 궁금합니다');



Question q1 = new Question();
q1.setSubject("안녕!");
q1.setContent("가입 인사드립니다 ^^");
this.questionRepository.save(q1);

Question q2 = new Question();
q2.setSubject("질문!");
q2.setContent("SpringBoot가 궁금합니다");
this.questionRepository.save(q2);



[H2 데이터베이스(In-Memory DB)]

http://h2database.com/

H2 데이터베이스는 주로 개발용이나 소규모 프로젝트에서 사용되는 파일 기반의 경량 데이터베이스
개발시에는 H2를 사용하여 빠르게 개발하고 실제 운영시스템은 좀 더 규모있는 DB를 사용
DB가 파일에 저장됨.

[특징]
H2 DB는 자바기반의 경량화된 데이터베이스
파일로 저장해서 실제 DB처럼 데이터 유지 가능
메모리 DB로 사용해서 실제 인스턴스가 동작하는 순간에만 유지
프로젝트 초기 개발에서 테스트 DB로 사용



*/
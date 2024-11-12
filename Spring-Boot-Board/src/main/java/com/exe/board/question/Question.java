package com.exe.board.question;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.exe.board.answer.Answer;
import com.exe.board.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Question {
	
	
	//id,subject,content,create_date
	//Entity는 데이터베이스 테이블과 매핑되는 자바 클래스
	//즉, 이 question 클래스는 db의 question 테이블과 매핑된다.
	//도메인 모델
	//이 안에 들어가는 변수는 컬럼으로 만들 것이다.
	
	@Id	//primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//1씩 증가
	private Integer id;
	
	@Column(length = 200)
	private String subject;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	//날짜를 저장하는 컬럼의 이름은 무조건 createdDate. 일종의 예약어.
	private LocalDateTime createdDate;
	
	
	//여기서 mappedBy는 answer.java의 question과 매핑된다. 즉 이름이 같아야한다.
	//질문을 지우면 answer도 다 지워라.
	//fetch eager : DB를 다 읽어오기 전까지 DB를 종료하지 말라.
	//Lazy : 기본타입, 그래서 test할 땐 끊긴다.
	//eager를 쓰지 않을 때는 repository에 @transactional을 추가한다.
	@OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE,
			fetch = FetchType.LAZY)	
	private List<Answer> answerList;
	
	@ManyToOne
	private SiteUser author;
	
	@ManyToMany
	Set<SiteUser> voter;
	//set은 중복값을 허용하지 않는다.
	
	private LocalDateTime modifyDate;

}

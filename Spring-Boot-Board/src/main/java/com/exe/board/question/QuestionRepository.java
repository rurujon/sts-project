package com.exe.board.question;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository 
	extends JpaRepository<Question, Integer>{
	//눈에 보이진 않지만 기본 메소드가 존재한다.
	//save, update(save), delete, findAll, findById
	
	//사용자 정의 메소드
	//findBy + 엔티티의 속성명
	Question findBySubject(String subject);
	Question findBySubjectAndContent(String subject, String content);
	List<Question> findBySubjectLike(String subject);
	
	Page<Question> findAll(Pageable pageable);
	
	
	/*
	
	
And (A and B)
findBySubjectAndContent(String subject, String content)	

Or (A or B)
findBySubjectOrContent(String subject, String content)
	
Between (컬럼명 between A and B)
findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate)
	
LessThan (컬럼명 < A)
findByIdLessThan(Integer id)	

GreaterThanEqual (컬럼명 >= A)
findByIdGraterThanEqual(Integer id) 

Like (컬럼명 like 'A%')
findBySubjectLike(String subject) 
 
In (컬럼명 in (A,B))
findBySubjectIn(String[] subjects)

OrderBy (order by 컬럼명)
findBySubjectOrderByCreateDateAsc(String subject)
	
	
	
	*/
	
}

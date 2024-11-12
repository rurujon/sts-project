package com.exe.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.exe.board.answer.Answer;
import com.exe.board.answer.AnswerRepository;
import com.exe.board.question.Question;
import com.exe.board.question.QuestionRepository;
import com.exe.board.question.QuestionService;

@SpringBootTest
class SpringBootBoardApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private AnswerRepository answerRepository; 
	
	@Autowired
	private QuestionService questionService;
	
	@Test
	void contextLoads() {
	}
	
	/*
	@Test
	void save() {
		
		Question q1 = new Question();
		q1.setSubject("스프링 부트가 무엇인가요?");
		q1.setContent("스프링 부트가 궁금해요");
		q1.setCreatedDate(LocalDateTime.now());
		
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("JPA가 무엇인가요?");
		q2.setContent("JPA가 궁금해요");
		q2.setCreatedDate(LocalDateTime.now());
		
		this.questionRepository.save(q2);
		
	}*/
	/*
	@Test
	void findAll() {
		
		List<Question> lists = questionRepository.findAll();
		
		//데이터 갯수가 2개인지 확인
		assertEquals(2, lists.size());
		
		//첫번째 데이터의 문자가 "스프링부트가 무엇인가요?" 확인
		Question q = lists.get(0);
		assertEquals("스프링 부트가 무엇인가요?", q.getSubject());
	}*/
	
	/*
	@Test
	void findBySubject() {
		
		Question q = questionRepository.findBySubject("스프링 부트가 무엇인가요?");
		
		assertEquals(1, q.getId());
	}*/
	
	/*
	@Test
	void findBySubjectAndContent() {
		
		Question q =
				questionRepository.findBySubjectAndContent("스프링 부트가 무엇인가요?", "스프링 부트가 궁금해요");
		
		assertEquals(1, q.getId());
	}*/
	
	/*
	@Test
	void findBySubjectLike() {
		
		List<Question> lists =
				questionRepository.findBySubjectLike("스프링%");
		
		Question q = lists.get(0);
		
		assertEquals("스프링 부트가 무엇인가요?", q.getSubject());
		
	}*/
	/*
	@Test
	void update() {
		
		//optional : 나오는 결과가 null일 수 있다.
		Optional<Question> op = questionRepository.findById(2);
		
		assertTrue(op.isPresent());
		
		Question q = op.get();
		q.setSubject("JPA가 무엇인가요?");
		q.setContent("JPA가 궁금해요");
		questionRepository.save(q);
	}
	*/
	/*
	@Test
	void delete() {
		
		assertEquals(2, questionRepository.count());
		
		Optional<Question> op = questionRepository.findById(2);
		
		assertTrue(op.isPresent());
		
		Question q = op.get();
		
		questionRepository.delete(q);
		
		assertEquals(1, questionRepository.count());
		
	}*/
	
	/*
	@Test
	void replySave() {
		
		Optional<Question> op = questionRepository.findById(1);
		
		assertTrue(op.isPresent());
		
		Question q = op.get();
		
		Answer an = new Answer();
		an.setContent("스프링은 어려워요");
		an.setQuestion(q);
		an.setCreatedDate(LocalDateTime.now());
		
		answerRepository.save(an);
	}*/
	
	/*
	@Test
	void replyFind() {
		
		Optional<Answer> op = answerRepository.findById(1);
		
		assertTrue(op.isPresent());
		
		Answer an = op.get();
		
		assertEquals(1, an.getQuestion().getId());
		assertEquals("스프링", an.getQuestion().getSubject());
	}*/
	
	
	/*
	//Transactional : 작업이 끝날 때까지 DB를 종료하지 않는다.
	@Transactional
	@Test
	void replyConnectQuestion() {
		
		Optional<Question> op = questionRepository.findById(1);
		
		assertTrue(op.isPresent());
		
		Question q = op.get();
		
		//--------------
		//test에서는 이 순간 DB가 끊겨버린다.
		//그래서 이후 코딩은 읽어오지 못하는 것.
		
		List<Answer> answerList = q.getAnswerList();
		
		assertEquals(2, answerList.size());
		assertEquals("스프링은 쉬워요", answerList.get(0).getContent());
	}*/
	
	/*
	@Test
	void save300() {
		
		for(int i=1;i<=300;i++) {
			
			String subject = String.format("가을이 오고있어요 : [%03d]", i);
			String content = String.format("곧 겨울도 오겠죠 : [%03d]", i);
			
			questionService.create(subject,content,null);
		}
		
	}*/
	

}

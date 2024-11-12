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
		q1.setSubject("������ ��Ʈ�� �����ΰ���?");
		q1.setContent("������ ��Ʈ�� �ñ��ؿ�");
		q1.setCreatedDate(LocalDateTime.now());
		
		this.questionRepository.save(q1);
		
		Question q2 = new Question();
		q2.setSubject("JPA�� �����ΰ���?");
		q2.setContent("JPA�� �ñ��ؿ�");
		q2.setCreatedDate(LocalDateTime.now());
		
		this.questionRepository.save(q2);
		
	}*/
	/*
	@Test
	void findAll() {
		
		List<Question> lists = questionRepository.findAll();
		
		//������ ������ 2������ Ȯ��
		assertEquals(2, lists.size());
		
		//ù��° �������� ���ڰ� "��������Ʈ�� �����ΰ���?" Ȯ��
		Question q = lists.get(0);
		assertEquals("������ ��Ʈ�� �����ΰ���?", q.getSubject());
	}*/
	
	/*
	@Test
	void findBySubject() {
		
		Question q = questionRepository.findBySubject("������ ��Ʈ�� �����ΰ���?");
		
		assertEquals(1, q.getId());
	}*/
	
	/*
	@Test
	void findBySubjectAndContent() {
		
		Question q =
				questionRepository.findBySubjectAndContent("������ ��Ʈ�� �����ΰ���?", "������ ��Ʈ�� �ñ��ؿ�");
		
		assertEquals(1, q.getId());
	}*/
	
	/*
	@Test
	void findBySubjectLike() {
		
		List<Question> lists =
				questionRepository.findBySubjectLike("������%");
		
		Question q = lists.get(0);
		
		assertEquals("������ ��Ʈ�� �����ΰ���?", q.getSubject());
		
	}*/
	/*
	@Test
	void update() {
		
		//optional : ������ ����� null�� �� �ִ�.
		Optional<Question> op = questionRepository.findById(2);
		
		assertTrue(op.isPresent());
		
		Question q = op.get();
		q.setSubject("JPA�� �����ΰ���?");
		q.setContent("JPA�� �ñ��ؿ�");
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
		an.setContent("�������� �������");
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
		assertEquals("������", an.getQuestion().getSubject());
	}*/
	
	
	/*
	//Transactional : �۾��� ���� ������ DB�� �������� �ʴ´�.
	@Transactional
	@Test
	void replyConnectQuestion() {
		
		Optional<Question> op = questionRepository.findById(1);
		
		assertTrue(op.isPresent());
		
		Question q = op.get();
		
		//--------------
		//test������ �� ���� DB�� ���ܹ�����.
		//�׷��� ���� �ڵ��� �о���� ���ϴ� ��.
		
		List<Answer> answerList = q.getAnswerList();
		
		assertEquals(2, answerList.size());
		assertEquals("�������� ������", answerList.get(0).getContent());
	}*/
	
	/*
	@Test
	void save300() {
		
		for(int i=1;i<=300;i++) {
			
			String subject = String.format("������ �����־�� : [%03d]", i);
			String content = String.format("�� �ܿﵵ ������ : [%03d]", i);
			
			questionService.create(subject,content,null);
		}
		
	}*/
	

}

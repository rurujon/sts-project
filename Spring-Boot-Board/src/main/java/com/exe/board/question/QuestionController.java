package com.exe.board.question;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import com.exe.board.answer.AnswerForm;
import com.exe.board.user.SiteUser;
import com.exe.board.user.UserService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {
	
	private final QuestionService questionService;
	private final UserService userService;
	
	@RequestMapping("/list")
	//@ResponseBody
	public String list(Model model, @PageableDefault Pageable pageable) {
		
		//Controller > Service > Repository
		//서비스라는 매개체를 쓰는 이유는 사용자가 DB에 접근하는 걸 막기 위함.
		Page<Question> paging = questionService.getList(pageable);
		

		model.addAttribute("paging",paging);
		
		return "question_list";
	}
	
	
	
	@RequestMapping("/detail/{id}")
	public String detail(Model model,@PathVariable("id") Integer id,
			AnswerForm answerForm) {
		
		Question question = questionService.getQuestion(id);
		
		model.addAttribute("question",question);
		
		return "question_detail";
	}
	
	
	@PreAuthorize("isAuthenticated")
	@GetMapping("/create")
	public String questionCreate(QuestionForm questionForm) {
		
		
		return "question_form";
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping("/create")
	public String questionCreate(@Valid QuestionForm questionForm,
			BindingResult bindingResult, Principal principal) {
		
		//매개변수의 @RequestParam은 지워도 된다.
		//BindingResult : 검사한 것에 대한 결과가 여기로 들어온다.
		//반드시 QuestionForm과 BindingResult의 위치가 바뀌면 안된다.
		
		if(bindingResult.hasErrors()) {
			
			
			
			return "question_form";
		}
		
		SiteUser siteUser = userService.getUser(principal.getName());
		
		questionService.create(questionForm.getSubject(), questionForm.getContent(),siteUser);
		
		
		return "redirect:/question/list";
	}
	
	
	//수정창 띄우기
	@PreAuthorize("isAuthenticated")
	@GetMapping("/modify/{id}")	//question/modify/302
	public String questionModify(QuestionForm questionForm,
			@PathVariable("id") Integer id, Principal principal) {
		
		Question question = questionService.getQuestion(id);
		
		if(!question.getAuthor().getUserName().equals(principal.getName())) {
			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"수정할 권한이 없습니다.");
			
		}
		
		//수정창에 띄울 데이터
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
		
	}
	
	@PreAuthorize("isAuthenticated")
	@PostMapping("/modify/{id}")	//question/modify/302
	public String questionModify(@Valid QuestionForm questionForm,
			BindingResult bindingResult, 
			@PathVariable("id") Integer id,
			Principal principal) {
		
		if(bindingResult.hasErrors()) {
			return "question_form";
		}
		
		Question question = questionService.getQuestion(id);
		
		if(!question.getAuthor().getUserName().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"수정할 권한이 없습니다");
		}
		
		questionService.modify(question, 
				questionForm.getSubject(), 
				questionForm.getContent());
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping("/delete/{id}")
	public String questionDelete(Principal principal,
			@PathVariable("id") Integer id) {
		
		Question question = questionService.getQuestion(id);
		
		questionService.delete(question);
		
		return "redirect:/";
		
	}
	
	@PreAuthorize("isAuthenticated")
	@GetMapping("/vote/{id}")
	public String questionVote(Principal principal,
			@PathVariable("id") Integer id) {
		
		Question question = questionService.getQuestion(id);
		SiteUser siteUser = userService.getUser(principal.getName());
		
		questionService.vote(question, siteUser);
		
		return String.format("redirect:/question/detail/%s", id);
	}
	
}

//템플릿 엔진 : 웹 애플리케이션에서 HTML, XML, 텍스트 문서 등을 동적으로 생성하기 위해 사용되는 도구
//자바 코드를 입력할 수 있는 xtml 형식의 파일

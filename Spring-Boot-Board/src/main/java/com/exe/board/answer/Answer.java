package com.exe.board.answer;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.exe.board.question.Question;
import com.exe.board.user.SiteUser;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Answer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(columnDefinition = "TEXT")
	private String content;
	
	private LocalDateTime createdDate;
	
	@ManyToOne
	private Question question;
	
	@ManyToOne
	private SiteUser author;
	
	@ManyToMany
	Set<SiteUser> voter;
	
	private LocalDateTime modifyDate;

}

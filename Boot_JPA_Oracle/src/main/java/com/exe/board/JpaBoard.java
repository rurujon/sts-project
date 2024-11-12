package com.exe.board;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;



import lombok.Data;

@Data
@Entity
@Table(name = "jpaBoard")
public class JpaBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bno;
	
	@Column(nullable = false, length = 30)
	private String title;
	
	@Column(nullable = false, length = 30)
	private String writer;
	
	@Column(length = 200)
	private String content;
	
	//처음 날짜
	@CreationTimestamp
	private Timestamp reqdate;
	
	//수정 날짜
	@UpdateTimestamp
	private Timestamp updatedate;
	

}

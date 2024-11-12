package com.web.oauth.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class BaseAuthUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String email;
	
	@Column
	private String picture;
	
	//JPA로 데이터베이스에 저장할때 Enum값을 기본 형태로 저장(기본값:int)
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BaseAuthRole role;
	
	@Builder
	public BaseAuthUser(String name,String email,
			String picture,BaseAuthRole role) {
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.role = role;
	}
	
	//회원정보 수정
	public BaseAuthUser update(String name,String picture) {
		this.name = name;
		this.picture = picture;
		
		return this;		
	}
	
	public String getRoleKey() {
		return this.role.getKey();
	}
	
}


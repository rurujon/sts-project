package com.exe.board.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.exe.board.user.UserSecurityService;

import lombok.RequiredArgsConstructor;

//환경 설정 파일로 만들겠다.
//모든 웹 주소 요청은 springsequrity 관리 하에 만들겠다.
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig {
	
	private final UserSecurityService userSecurityService;
	
	//Bean : 객체 생성.
	//이 클래스를 객체생성한다.
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		//모든 /** 를 허용하겠다.
		http.authorizeRequests().antMatchers("/**").permitAll()
		.and()
		.csrf().ignoringAntMatchers("/h2-console/**")
		.and()
		.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
				XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
		.and()
		.formLogin().loginPage("/user/login").defaultSuccessUrl("/")
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
		.logoutSuccessUrl("/").invalidateHttpSession(true)
		;
		
		
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	//AuthenticationManager는 스프링 시큐리티의 인증을 담당
	//UserSecurityService와 PasswordEncoder(암호화)가 자동으로 설정되어 비교.
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authenticationConfiguration) 
					throws Exception{
		
		return authenticationConfiguration.getAuthenticationManager();
	}

}

package com.web.oauth.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.web.oauth.base.model.BaseAuthRole;
import com.web.oauth.base.service.BaseCustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration	//이 애노테이션은 해당 클래스가 스프링의 설정 클래스임을 나타냅니다. 스프링 컨테이너에 의해 이 클래스가 빈으로 등록됩니다.
@EnableWebSecurity	//스프링 시큐리티 웹 보안을 활성화합니다. 이를 통해 기본적인 보안 설정을 적용할 수 있습니다.
@RequiredArgsConstructor	//Lombok의 애노테이션으로, final로 선언된 필드에 대한 생성자를 자동으로 생성합니다
public class WebSecurityConfig {
	
	@Autowired	//스프링의 의존성 주입 애노테이션으로, BaseCustomOAuth2UserService의 인스턴스를 자동으로 주입받습니다.
	private final BaseCustomOAuth2UserService baseCustomOAuth2UserService;
	
	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception{
		
		http
		.csrf().disable().headers().frameOptions().disable()	//CSRF(Cross-Site Request Forgery) 공격을 방지하는 기능을 비활성화합니다. X-Frame-Options 헤더를 비활성화하여 웹 페이지를 iframe으로 로드할 수 있도록 허용합니다. 이는 주로 H2 데이터베이스 콘솔을 사용할 때 필요합니다.
		.and()
		.authorizeRequests()	//HTTP 요청에 대한 보안 설정을 시작합니다. 여기에서 어떤 요청이 인증을 요구하는지 설정할 수 있습니다.
		.antMatchers("/","/css/**","/images/**","/js/**","/h2-console/**").permitAll()	//특정 URL 패턴에 대한 요청을 지정합니다. / 지정된 패턴에 대한 요청은 인증 없이 접근할 수 있도록 허용합니다. 여기서는 루트 URL 및 정적 자원(css, 이미지, js, H2 콘솔)에 대한 접근을 허용합니다.
		.antMatchers("/api/vi/**").hasRole(BaseAuthRole.USER.name())	///api/vi/** 패턴에 대한 요청을 지정합니다. / 이 패턴에 대한 요청은 USER 역할을 가진 사용자만 접근할 수 있도록 제한합니다.
		.anyRequest().authenticated()	//지정되지 않은 모든 요청을 의미합니다 / 모든 요청은 인증된 사용자만 접근할 수 있도록 요구합니다. 즉, 위에서 허용되지 않은 모든 요청은 인증이 필요합니다
		.and()
		.logout().logoutSuccessUrl("/")	//로그아웃 관련 설정을 시작합니다. logoutSuccessUrl("/"): 로그아웃 성공 후 리다이렉션할 URL을 설정합니다. 여기서는 루트 URL로 리다이렉트합니다.
		.and()
		.oauth2Login().defaultSuccessUrl("/").userInfoEndpoint()	//oauth2Login(): OAuth2 로그인 관련 설정을 시작합니다. defaultSuccessUrl("/"): OAuth2 로그인 성공 후 리다이렉트할 기본 URL을 설정합니다. userInfoEndpoint(): OAuth2 사용자 정보 엔드포인트 설정을 시작합니다.	
		.userService(baseCustomOAuth2UserService)	//OAuth2 로그인 시 사용자의 정보를 처리할 사용자 정의 서비스를 설정합니다. baseCustomOAuth2UserService는 이 역할을 수행하는 서비스입니다.
		;
		
		return http.build();	//설정이 완료된 HttpSecurity 객체를 기반으로 SecurityFilterChain을 생성하고 반환합니다. 이 객체는 실제 보안 필터 체인을 정의합니다.
		
	}
	

}



package com.web.oauth.base.service;

import java.util.Collections;

import javax.persistence.Entity;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.web.oauth.base.dao.BaseAuthUserRepository;
import com.web.oauth.base.dto.OAuthAttributes;
import com.web.oauth.base.dto.SessionUser;
import com.web.oauth.base.model.BaseAuthUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BaseCustomOAuth2UserService 
	implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	@Autowired
	private final BaseAuthUserRepository baseAuthUserRepository;
	
	@Autowired
	private final HttpSession httpSession;
		
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oauthUserService =
				new DefaultOAuth2UserService();
		
		OAuth2User oauth2User = oauthUserService.loadUser(userRequest);
		
		//간편 로그인을 진행하는 플렛폼(google,kakao,naver)
		String registrationId = 
				userRequest.getClientRegistration().getRegistrationId();
		
		//OAuth2로그인 진행시에 key가되는 필드값(primary key역할)
		//구글:sub, 네이버:response, 카카오:id
		String userNameAttributeName = 
				userRequest
				.getClientRegistration()
				.getProviderDetails()
				.getUserInfoEndpoint()
				.getUserNameAttributeName();
				
		//필드값을 확인
		System.out.println(userNameAttributeName);//sub
		
		//로그인을 통해 가져온 OAuth2User의 속성을 담아두는 of메소드
		OAuthAttributes attributes =
				OAuthAttributes.of(registrationId, 
						userNameAttributeName, 
						oauth2User.getAttributes());
		
		//응답받은 속성 확인(Json형태의 데이터)
		System.out.println(attributes.getAttributes());
		
		//응답받은 속성을 authUser 객체에 넣음
		BaseAuthUser authUser = saveOrUpdate(attributes);
		
		//세션에 사용자 정보를 저장하기위한 DTO클래스
		httpSession.setAttribute("user", new SessionUser(authUser));
		
		return new DefaultOAuth2User(
				Collections.singleton(
					new SimpleGrantedAuthority(authUser.getRoleKey())),
				attributes.getAttributes(),
				attributes.getNameAttributeKey());
		
	}

	//구글사용자 정보가 업데이트 되었을때를 위한 메소드
	//사용자의 이름이나 프로필 사진이 변경되면 User의 엔티티에도 반영됨
	private BaseAuthUser saveOrUpdate(OAuthAttributes attributes) {
		
		BaseAuthUser authUser = 
				baseAuthUserRepository.findByEmail(attributes.getEmail())
				.map(entity -> entity.update(attributes.getName(),
						attributes.getPicture()))
				.orElse(attributes.toEntity());
		
		return baseAuthUserRepository.save(authUser);
		
	}
	
	
	
	
}




/*
[Kakao Attribute] - id

{
id=2340739652, 
connected_at=2022-12-14T03:58:53Z, 
properties=
	{
	nickname=세상은 , 
	profile_image=http://k.kakaocdn.net/dn/Kfxs8/btr1111lT/fkZPX2K3ebIYjAHunGTYK0/img_640x640.jpg, 
	thumbnail_image=http://k.kakaocdn.net/dn/Kfxs8/btr111b1lT/fkZPX2K3ebIYjAHunGTYK0/img_110x110.jpg
	}, 
kakao_account=
	{
	profile_nickname_needs_agreement=false, 
	profile_image_needs_agreement=false, 
	profile=
		{
		nickname=세상은 , 
		thumbnail_image_url=http://k.kakaocdn.net/dn/Kfxs8/btr111rb1lT/fkZPX2K3ebIYjAHunGTYK0/img_110x110.jpg, 
		profile_image_url=http://k.kakaocdn.net/dn/Kfxs8/btrII111lT/fkZPX2K3ebIYjAHunGTYK0/img_640x640.jpg,
		}, 
	has_email=true, 
	email_needs_agreement=false, 
	is_email_valid=true, 
	is_email_verified=true, 
	email=suzi@naver.com 	***안 넘겨줌
	}
}

-------------------------------------------


[Naver Attribute] - response

{
id=ApWwLB5hGFlNvLcBpAU8LNpjbkZ2UyFt-PLmSlpWJt8, 
profile_image=https://phinf.pstatic.net/contact/20220715_138/1657872143804gFYAa_PNG/avatar_profile.png, 
email=suzi@naver.com, 
name=배수지
}
 */






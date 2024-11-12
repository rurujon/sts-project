package com.web.oauth.base.dto;

import java.util.Map;

import com.web.oauth.base.model.BaseAuthRole;
import com.web.oauth.base.model.BaseAuthUser;

import lombok.Builder;
import lombok.Getter;

//Google에서 넘어오는 데이터를 담아두는 DTO

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
	
	private String nameAttributeKey;
	private String name;
	private String email;
	private String picture;
	
	
	@Builder
	public OAuthAttributes(Map<String, Object> attributes,
			String nameAttributeKey,String name,String email,String picture) {
		
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
		
	}
	
	
	//구글,카카오,네이버등을 구분
	public static OAuthAttributes of(String registrationId,
			String userNameAttributeName,
			Map<String, Object> attributes) {
		
		if(registrationId.equals("kakao")) {
			return ofKakao(userNameAttributeName,attributes);	//"id"
		}else if(registrationId.equals("naver")) {
			return ofNaver("id",attributes);
		}else {
			//userNameAttributeName : sub
			return ofGoogle(userNameAttributeName, attributes);
		}
		
		
		
		
	}
	
	
	private static OAuthAttributes ofKakao(String userNameAttributeName,
			Map<String, Object> attributes) {
		
		Map<String, Object> kakaoAccount =
				(Map<String, Object>)attributes.get("kakao_account");
		
		Map<String, Object> kakaoProfile =
				(Map<String, Object>)kakaoAccount.get("profile");
		
		return OAuthAttributes.builder()
				.name((String)kakaoProfile.get("nickname"))
				.email("suzi@naver.com")
				.picture((String)kakaoProfile.get("profile_image_url"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
		
	}
	
	private static OAuthAttributes ofNaver(String userNameAttributeName,
			Map<String, Object> attributes) {
		
		Map<String, Object> response =
				(Map<String, Object>)attributes.get("response");
		
		return OAuthAttributes.builder()
				.name((String)response.get("name"))
				.email((String)response.get("email"))
				.picture((String)response.get("profile_image"))
				.attributes(response)
				.nameAttributeKey(userNameAttributeName)
				.build();
		
	}
	
	
	private static OAuthAttributes ofGoogle(String userNameAttributeName,
			Map<String, Object> attributes) {
		
		return OAuthAttributes.builder()
				.name((String)attributes.get("name"))
				.email((String)attributes.get("email"))
				.picture((String)attributes.get("picture"))
				.attributes(attributes)
				.nameAttributeKey(userNameAttributeName)
				.build();
	}
	
	
	public BaseAuthUser toEntity() {
		
		return BaseAuthUser.builder()
				.name(name)
				.email(email)
				.picture(picture)
				.role(BaseAuthRole.GUEST)				
				.build();
		
	}
	
	
}


//





package com.exe.board.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

//서버에서 읽어온것과 db에서 읽어온 것을 비교해주는 클래스
@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
	
	
		
		//사용자명으로 siteUser 객체를 조회
		Optional<SiteUser> searchUser =
				userRepository.findByUserName(userName);
		
		//사용자명에 해당하는 데이터 확인
		if(!searchUser.isPresent()) {	//jdk11이상(isEmpty())
			throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
		}
		
		SiteUser siteUser = searchUser.get();
		
		List<GrantedAuthority> authorities =
				new ArrayList<GrantedAuthority>();
		
		//사용자명이 admin이면 ADMIN 권한 부여
		if("admin".equals(userName)) {
			authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
		}else {	//그 외에 user 권한 부여
			authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
		}
	
		//사용자명, 비밀번호, 권한을 가지고 스프링의 user 객체를 생성해서 리턴
		return new User(siteUser.getUserName(),
				siteUser.getPassword(), authorities);
	}	
	

}

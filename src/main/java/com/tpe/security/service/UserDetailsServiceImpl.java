package com.tpe.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userRepository.findByUserName(username).
				orElseThrow(()->new ResourceNotFoundException("User not found username:"+username));
		
		if(user!=null) {
			return new org.springframework.security.core.userdetails.
					User(user.getUserName(), user.getPassword(),buildGrantedAuthorities(user.getRoles()));
		}else {
			throw new UsernameNotFoundException("User not found username:"+username);
		}
		
	}
	
	private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<Role> roles){
		List<SimpleGrantedAuthority> authorities=new ArrayList<>();
		for(Role role:roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName().name()));
		}
		
		return authorities;
	}
	
	

}

package com.kashyap.reg.security;

import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kashyap.reg.entity.User;
import com.kashyap.reg.repository.UserRepository;

//UserDetailsSerivce is a interface provided by spring which has loadByUser method that loads user form the database
//If user is not null,we are adding the user to the spring provided user class
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;
	

	public CustomUserDetailsService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(username);
		if(user!=null) {
			return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword()
					,user.getRoles().stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
		}
		else{
			throw new UsernameNotFoundException("Invalid Username or Password");
		}
	}

}

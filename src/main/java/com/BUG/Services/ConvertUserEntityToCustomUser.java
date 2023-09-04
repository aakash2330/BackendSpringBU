package com.BUG.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.BUG.Entities.CustomUserDetails;
import com.BUG.Repositories.UserRepo;

@Component
public class ConvertUserEntityToCustomUser implements UserDetailsService {
	
	@Autowired
	UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUserDetails customUser = new CustomUserDetails(userRepo.findByusername(username));
		return customUser;
	}

}

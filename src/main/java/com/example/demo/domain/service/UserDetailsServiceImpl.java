package com.example.demo.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mapper.UsersMapper;
import com.example.demo.domain.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private UsersMapper mapper;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = mapper.selectOne(username);
		return user;
	}

}

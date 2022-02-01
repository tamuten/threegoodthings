package com.example.demo.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.mapper.UsersMapper;
import com.example.demo.domain.model.User;

@Repository
public class UsersDao {
	@Autowired
	private UsersMapper mapper;

	public void insertOne(User user) {
		mapper.insertOne(user);
	}
}

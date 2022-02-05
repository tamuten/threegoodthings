package com.example.demo.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.User;

@Mapper
public interface UsersMapper {
	public void insertOne(User user);
	public User selectOne(String mailAddress);
}

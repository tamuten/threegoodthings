package com.example.demo.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.TmpUser;

@Mapper
public interface TmpUserMapper {
	public int insertOne(TmpUser tmpUser);
	public TmpUser findByToken(String token);
}

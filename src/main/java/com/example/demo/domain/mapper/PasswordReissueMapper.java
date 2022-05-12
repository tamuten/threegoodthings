package com.example.demo.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.PasswordReissueInfo;

@Mapper
public interface PasswordReissueMapper {
	int insertOne(PasswordReissueInfo info);
	PasswordReissueInfo findOne(String token);
	int deleteByToken(String token);
}

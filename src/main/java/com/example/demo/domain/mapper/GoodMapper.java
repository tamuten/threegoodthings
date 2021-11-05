package com.example.demo.domain.mapper;

import java.sql.Date;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Good;

@Mapper
public interface GoodMapper {
	public void insertOne(Good good);
	public Good selectOne(Date date);
}

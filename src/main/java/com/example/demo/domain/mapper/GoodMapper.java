package com.example.demo.domain.mapper;

import java.sql.Date;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Good;

@Mapper
public interface GoodMapper {
	public void insertOne(String good, int num, Date date);
	public Good selectOne(Date date);
	public int count(Date date);
	public void updateOne(String good, int num, Date date);
}

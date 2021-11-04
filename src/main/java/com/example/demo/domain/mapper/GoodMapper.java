package com.example.demo.domain.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Good;

@Mapper
public interface GoodMapper {
	public void insertOne(Good good);
}

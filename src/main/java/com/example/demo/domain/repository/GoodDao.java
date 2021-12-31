package com.example.demo.domain.repository;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.mapper.GoodMapper;
import com.example.demo.domain.model.Good;

@Repository
public class GoodDao {
	@Autowired
	private GoodMapper goodMapper;

	public void insert(String good, int num, Date date) {
		goodMapper.insertOne(good, num, date);
	}

	public Good selectOne(Date date) {
		return goodMapper.selectOne(date);
	}

	public int count(Date date) {
		return goodMapper.count(date);
	}

	public void updateOne(String good, int num, Date date) {
		goodMapper.updateOne(good, num, date);
	}
}

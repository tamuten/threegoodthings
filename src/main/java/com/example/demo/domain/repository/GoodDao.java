package com.example.demo.domain.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.mapper.GoodMapper;
import com.example.demo.domain.model.Good;

@Repository
public class GoodDao {
	@Autowired
	private GoodMapper goodMapper;

	public void insert(final String good, final int num, final Date date) {
		goodMapper.insertOne(good, num, date);
	}

	public List<Good> findAll() {
		return goodMapper.findAll();
	}

	public List<Good> likeSearch(final String keyword) {
		return goodMapper.likeSearch(keyword);
	}

	public Good selectOne(final String mailAddress, final Date targetDate) {
		return goodMapper.selectOne(mailAddress, targetDate);
	}

	public int count(final Date targetDate) {
		return goodMapper.count(targetDate);
	}

	public void updateOne(final String good, final int num, final Date date) {
		goodMapper.updateOne(good, num, date);
	}
}

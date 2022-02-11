package com.example.demo.domain.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Good;

@Mapper
public interface GoodMapper {
	public void insertOne(final String mailAddress, final String good, final int num, final Date date);
	public List<Good> findAll(final String mailAddress);
	public List<Good> likeSearch(final String mailAddress, final String keyword);
	public Good selectOne(final String mailAddress, final Date targetDate);
	public int count(final String mailAddress, final Date targetDate);
	public void updateOne(final String mailAddress, final String good, final int num, final Date date);
}

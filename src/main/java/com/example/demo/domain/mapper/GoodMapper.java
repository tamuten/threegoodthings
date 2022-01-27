package com.example.demo.domain.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.model.Good;

@Mapper
public interface GoodMapper {
	public void insertOne(final String good, final int num, final Date date);
	public List<Good> findAll();
	public List<Good> likeSearch(final String keyword);
	public Good selectOne(final Date date);
	public int count(final Date date);
	public void updateOne(final String good, final int num, final Date date);
}

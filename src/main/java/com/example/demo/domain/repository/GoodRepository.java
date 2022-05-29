package com.example.demo.domain.repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.example.demo.domain.mapper.GoodMapper;
import com.example.demo.domain.model.Good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GoodRepository {
  @Autowired
  private GoodMapper goodMapper;

  public void insert(final String mailAddress, final String good, final int num, final Date date) {
    goodMapper.insertOne(mailAddress, good, num, date);
  }

  public List<Good> findAll(final String mailAddress) {
    return goodMapper.findAll(mailAddress);
  }

  public List<Good> likeSearch(final String mailAddress, final String keyword) {
    return goodMapper.likeSearch(mailAddress, keyword);
  }

  public Good findByDate(final String mailAddress, final LocalDate targetDate) {
    return goodMapper.selectOne(mailAddress, targetDate);
  }

  public int countByDateAndUser(final String mailAddress, final Date targetDate) {
    return goodMapper.count(mailAddress, targetDate);
  }

  public void updateOne(final String mailAddress, final String good, final int num, final Date date) {
    goodMapper.updateOne(mailAddress, good, num, date);
  }

  public List<LocalDate> findMonthlyPosts(final String mailAddress, final LocalDate firstDate,
      final LocalDate lastDate) {
    return goodMapper.findMonthlyPosts(mailAddress, firstDate, lastDate);
  }
}

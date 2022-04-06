package com.example.demo.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.model.TmpUser;

public interface TmpUserDao extends JpaRepository<TmpUser, Integer> {
}

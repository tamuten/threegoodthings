package com.example.demo.domain.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MonthlyPost {
  private LocalDate localDate;
  private boolean isFullyPosted;
}

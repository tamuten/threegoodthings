package com.example.demo.util.calendar;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PostingDate {
  LocalDate localDate;
  boolean isPosted;
}

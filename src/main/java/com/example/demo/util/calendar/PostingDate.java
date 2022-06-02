package com.example.demo.util.calendar;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostingDate {
  LocalDate localDate;
  boolean isPosted;
  boolean isToday;
}

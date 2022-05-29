package com.example.demo.form;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class MainForm {
  private String good1;
  private String good2;
  private String good3;
  // hidden項目
  @DateTimeFormat(pattern = "yyyy/MM/dd")
  private LocalDate date;

  public MainForm(LocalDate date) {
    this.date = date;
  }
}

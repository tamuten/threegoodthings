package com.example.demo.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser {
  private LocalDateParser() {
  }

  // TODO:formatは列挙型にしたほうがいい
  // TODO:dateが日付型でなかった時の処理を追加すべき
  public static LocalDate toLocalDate(String date, String format) {
    if (date == null) {
      return null;
    }

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
    return LocalDate.parse(date, dateTimeFormatter);
  }
}

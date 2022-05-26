package com.example.demo.domain.service;

import static com.example.demo.util.LocalDateParser.toLocalDate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarServiceTestHelper {
  private static final String FORMAT_DATE_SLASH = "yyyy/MM/dd";

  public static List<LocalDate> generateExpectedList() {
    List<LocalDate> expectedList = new ArrayList<>();
    expectedList.add(toLocalDate("2022/02/27", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/02/28", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/01", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/02", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/03", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/04", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/05", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/06", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/07", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/08", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/09", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/10", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/11", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/12", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/13", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/14", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/15", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/16", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/17", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/18", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/19", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/20", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/21", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/22", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/23", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/24", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/25", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/26", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/27", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/28", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/29", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/30", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/03/31", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/04/01", FORMAT_DATE_SLASH));
    expectedList.add(toLocalDate("2022/04/02", FORMAT_DATE_SLASH));

    return expectedList;
  }
}

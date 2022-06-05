package com.example.demo.domain.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.example.demo.dataset.CsvDataSetLoader;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class
})
@Transactional
public class GoodRepositoryTest {
  @Autowired
  GoodRepository goodRepository;

  private static final List<LocalDate> EXPECTED_MONTHLY_POSTS = new ArrayList<>() {
    {
      add(LocalDate.of(2022, 3, 10));
      add(LocalDate.of(2022, 3, 15));
      add(LocalDate.of(2022, 3, 23));
    }
  };

  void findMonthlyPostsは指定された範囲で日記の記入がある日のリストを取得する() {
    // List<LocalDate> actualList =
    // goodRepository.findMonthlyPosts("tamuten310@gmail.com", LocalDate.of(2022, 5,
    // 1),
    // LocalDate.of(2022, 6, 4));

    // assertThat(actualList.size()).isEqualTo(EXPECTED_MONTHLY_POSTS.size());
    // assertThat(actualList).isEqualTo(EXPECTED_MONTHLY_POSTS);
  }
}

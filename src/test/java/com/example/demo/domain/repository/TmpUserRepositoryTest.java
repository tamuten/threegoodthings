package com.example.demo.domain.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dataset.CsvDataSetLoader;
import com.example.demo.domain.model.TmpUser;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;

@SpringBootTest
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class) // DBUnitでCSVファイルを使えるよう指定。
@TestExecutionListeners({
		DependencyInjectionTestExecutionListener.class, // このテストクラスでDIを使えるように指定
		TransactionDbUnitTestExecutionListener.class // @DatabaseSetupや＠ExpectedDatabaseなどを使えるように指定
})
@Transactional
public class TmpUserRepositoryTest {
	@Autowired
	private TmpUserRepository repository;

	@Disabled
	@Test
	@DatabaseSetup("/testdata/TmpUserRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/TmpUserRepositoryTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void createはTmpUserのデータを過不足なく登録する() {
		// Setup
		TmpUser tmpUser = TmpUser.builder()
			.mailAddress("tanakakei@example.com")
			.password("4c7572b1e8fa332051c05907cbfcf9ed3f50cc52")
			.token("9c412fcb-9155-4611-b03e-8b1a1a54b54b")
			.expiryDate(LocalDateTime.parse("2022-4-30T10:15:30", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
			.build();

		// Execute
		int createdCnt = repository.create(tmpUser);

		// Verify
		assertThat(createdCnt).isEqualTo(1);

	}

}

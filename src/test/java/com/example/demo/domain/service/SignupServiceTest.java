package com.example.demo.domain.service;

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
public class SignupServiceTest {
	@Autowired
	private SignupService tmpUserService;

	@Disabled
	@Test
	@DatabaseSetup("/testdata/TmpUserServiceTest/init-data")
	@ExpectedDatabase(value = "/testdata/TmpUserServiceTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
	void createはmailAddressとpassword指定した通りに登録する() {
		// Setup
		TmpUser tmpUser = TmpUser.builder()
			.mailAddress("tanakakei@example.com")
			.password("4c7572b1e8fa332051c05907cbfcf9ed3f50cc52")
			.uuid("9c412fcb-9155-4611-b03e-8b1a1a54b54b")
			.build();

		// Execute
		//		TmpUser expected = tmpUserService.createAndSendMail(tmpUser);
		//
		//		// Verify
		//		assertThat(expected.getMailAddress()).isEqualTo("tanakakei@example.com");
		//		assertThat(expected.getPassword()).isEqualTo("4c7572b1e8fa332051c05907cbfcf9ed3f50cc52");
		//		assertThat(expected.getUuid()).isEqualTo("9c412fcb-9155-4611-b03e-8b1a1a54b54b");
	}
}

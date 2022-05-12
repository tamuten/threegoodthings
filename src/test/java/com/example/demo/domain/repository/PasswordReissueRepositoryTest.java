package com.example.demo.domain.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dataset.CsvDataSetLoader;
import com.example.demo.domain.model.PasswordReissueInfo;
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
public class PasswordReissueRepositoryTest {
	@Autowired
	PasswordReissueRepository repository;

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createは60文字のメールアドレスと60文字のトークンで正しく登録される() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT");
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX");

		int createdCnt = repository.create(newInfo);

		assertThat(createdCnt).isEqualTo(1);
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createは61文字のメールアドレスでエラーをスローする() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT+");
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX");

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createは61文字のトークンでエラーをスローする() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT");
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX+");

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void findByTokenは一致するトークンを持つ行を返却する() {
		PasswordReissueInfo expected = new PasswordReissueInfo();
		expected.setMailAddress("tamuten310@gmail.com");
		expected.setToken("911d920b-4ff5-4aab-8fd1-003cd52c024b");

		PasswordReissueInfo actual = repository.findByToken("911d920b-4ff5-4aab-8fd1-003cd52c024b");
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/after-delete-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void deleteByTokenは一致するトークンを持つ行を削除する() {
		int deletedCnt = repository.deleteByToken("911d920b-4ff5-4aab-8fd1-003cd52c024b");
		assertThat(deletedCnt).isEqualTo(1);
	}
}

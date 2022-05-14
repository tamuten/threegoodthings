package com.example.demo.domain.repository;

import static com.example.demo.util.LocalDateTimeParser.*;
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
	private static final String FORMAT_DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createは60文字のメールアドレスと60文字のトークンと日付で正しく登録される() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT");
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX");
		newInfo.setExpiryDate(toLocalDateTime("2022/05/20 12:00:00", FORMAT_DATETIME_SLASH));

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
		newInfo.setExpiryDate(toLocalDateTime("2022/05/20 12:00:00", FORMAT_DATETIME_SLASH));

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createはメールアドレスがnullでエラーを投げる() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress(null);
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX");
		newInfo.setExpiryDate(toLocalDateTime("2022/05/20 12:00:00", FORMAT_DATETIME_SLASH));

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createは61文字のトークンでエラーをスローする() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT");
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX+");
		newInfo.setExpiryDate(toLocalDateTime("2022/05/20 12:00:00", FORMAT_DATETIME_SLASH));

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createはトークンがnullでエラーを投げる() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT");
		newInfo.setToken(null);
		newInfo.setExpiryDate(toLocalDateTime("2022/05/20 12:00:00", FORMAT_DATETIME_SLASH));

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void createはexpiryDateがnullでエラーを投げる() {
		PasswordReissueInfo newInfo = new PasswordReissueInfo();
		newInfo.setMailAddress("DYjTZS7t9GJXAygRPT9LMx64cXQd8kagC@cH6iMFzb8Mft43hdrN.7HLULxT");
		newInfo.setToken("ifYw5RefUVnsLcRyPfaYVX4upQQe9KpDGLYVwAFRwhf7QMyC4LTs47A9ZKaX");
		newInfo.setExpiryDate(null);

		assertThrows(DataIntegrityViolationException.class, () -> repository.create(newInfo));
	}

	@Test
	@DatabaseSetup("/testdata/PasswordReissueRepositoryTest/init-data")
	@ExpectedDatabase(value = "/testdata/PasswordReissueRepositoryTest/init-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
	void findByTokenは一致するトークンを持つ行を返却する() {
		PasswordReissueInfo expected = new PasswordReissueInfo();
		expected.setMailAddress("tamuten310@gmail.com");
		expected.setToken("911d920b-4ff5-4aab-8fd1-003cd52c024b");
		expected.setExpiryDate(toLocalDateTime("2019/03/01 10:00:00", FORMAT_DATETIME_SLASH));

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

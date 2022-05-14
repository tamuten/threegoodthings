package com.example.demo.domain.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dataset.CsvDataSetLoader;
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
public class UsersRepositoryTest {
    @Autowired
    UsersRepository repository;

    @Disabled
    @Test
    @DatabaseSetup("/testdata/UsersRepositoryTest/init-data")
    @ExpectedDatabase(value = "/testdata/UsersRepositoryTest/after-update-password-data", assertionMode = DatabaseAssertionMode.NON_STRICT)
    void updatePasswordは指定されたメールアドレスのユーザーのパスワードを指定された通りに変更する() {
        int updateCnt = repository.updatePassword("tamuten310@gmail.com", "8151325dcdbae9e0ff95f9f9658432dbedfdb209");
        assertThat(updateCnt).isEqualTo(1);
    }
}

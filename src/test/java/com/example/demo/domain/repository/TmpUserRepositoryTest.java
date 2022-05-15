package com.example.demo.domain.repository;

import static com.example.demo.util.LocalDateTimeParser.*;
import static org.assertj.core.api.Assertions.*;

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
    private static final String FORMAT_DATETIME_SLASH = "yyyy/MM/dd HH:mm:ss";

    @Test
    @DatabaseSetup("/testdata/TmpUserRepositoryTest/init-data")
    @ExpectedDatabase(value = "/testdata/TmpUserRepositoryTest/after-create-data", assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED)
    void createはTmpUserのデータを過不足なく登録する() {
        // Setup
        TmpUser tmpUser = TmpUser.builder()
            .mailAddress("tanakakei@example.com")
            .password("4c7572b1e8fa332051c05907cbfcf9ed3f50cc52")
            .token("9c412fcb-9155-4611-b03e-8b1a1a54b54b")
            .expiryDate(toLocalDateTime("2022/04/30 10:15:30", FORMAT_DATETIME_SLASH))
            .build();

        // Execute
        int createdCnt = repository.create(tmpUser);

        // Verify
        assertThat(createdCnt).isEqualTo(1);
    }

}

package com.example.backtest.repository;

import com.example.backtest.model.Language;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:db-test.properties")
public class LanguageRepositoryTest {

    @Autowired
    private LanguageRepository languageRepository;

    @Test(expected = DataIntegrityViolationException.class)
    public void GivenExistingName_WhenCreateLanguageWithSameName_ThenShouldFail() {
        // Given
        final String name = "Spanish";

        Language language = new Language();

        language.setName(name);
        // Then
        this.languageRepository.saveAndFlush(language);
    }
}

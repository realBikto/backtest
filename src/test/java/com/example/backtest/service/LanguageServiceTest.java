package com.example.backtest.service;

import com.example.backtest.model.Language;
import com.example.backtest.repository.LanguageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class LanguageServiceTest {

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageRepository languageRepository;

    @Test
    public void GivenExistingLanguageID_WhenGetLanguage_ThenReturnsLanguage() throws Exception {
        // Given
        final Long languageid = 1L;

        // When
        final Language language = this.languageService.get(languageid);

        // Then
        assertThat(language, is(notNullValue()));
        assertThat(language.getLanguageid(), is(languageid));
        assertThat(language.getName(), is(notNullValue()));
    }

    @Test
    public void GivenUnexistingLanguageID_WhenGetLanguage_ThenReturnsErrorMessage() {
        // Given
        final Long languageid = -2L;
        try {
            // When
            this.languageService.get(languageid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language -2 not found."));
        }
    }

    @Test
    public void GivenNullLanguageID_WhenGetLanguage_ThenReturnsErrorMessage() {
        // Given
        final Long languageid = null;

        try {
            // When
            this.languageService.get(languageid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("languageid cannot be null"));
        }
    }

    @Test
    public void GivenNonExistingLanguageObject_WhenCreateLanguage_ThenCreatesLanguage() throws Exception {
        // Given
        final String newLanguage = "Chinese";

        Language language = new Language();
        language.setName(newLanguage);

        // When
        final Language languageid = this.languageService.create(language);

        // Then
        assertThat(languageid.getLanguageid(), is(notNullValue()));
        assertThat(language.getName(), is(equalTo(newLanguage)));
    }

    @Test
    public void GivenExistingName_WhenCreateLanguage_ThenShouldFail() {
        // Given
        final String newLanguage = "Spanish";

        Language language = new Language();
        language.setName(newLanguage);

        try {
            // When
            this.languageService.create(language);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language with name 'Spanish' already exists"));
        }
    }

    @Test
    public void GivenNullLanguage_WhenCreateLanguage_ThenReturnsErrorMessage() {
        // Given
        final Language language = null;

        try {
            // When
            this.languageService.create(language);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language cannot be null"));
        }
    }

    @Test
    public void GivenNullName_WhenCreateLanguage_ThenReturnsErrorMessage() {
        // Given
        final Language language = new Language();

        try {
            // When
            this.languageService.create(language);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Name cannot be null"));
        }
    }

    @Test
    public void GivenRealLanguageidAndNonExistingLanguage_WhenUpdateLanguage_ThenShouldUpdate() throws Exception {
        // Given
        final Long languageid = 1L;
        final String newName = "Japanese";

        Language updatedLanguage = new Language();
        updatedLanguage.setName(newName);

        // When
        Language languageDB = this.languageService.update(languageid,updatedLanguage);

        // Then
        assertThat(languageDB.getLanguageid(), is(equalTo(languageid)));
        assertThat(languageDB.getName(), is(equalTo(newName)));
    }

    @Test
    public void GivenRealLanguageidAndExistingName_WhenUpdateLanguage_ThenShouldFail() {
        // Given
        final Long languageid = 2L;

        Language language = new Language();
        language.setName("Spanish");
        try {
            // When
            this.languageService.update(languageid, language);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language with name Spanish already exists"));
        }
    }

    @Test
    public void GivenNullLanguage_WhenUpdateLanguage_ThenReturnsErrorMessage() {
        // Given
        final Long languageid = 2L;
        final Language language = null;

        try {
            // When
            this.languageService.update(languageid, language);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language cannot be null"));
        }
    }

    @Test
    public void GivenNullName_WhenUpdateLanguage_ThenReturnsErrorMessage() {
        // Given
        final Long languageid = 2L;
        final Language language = new Language();

        try {
            // When
            this.languageService.update(languageid, language);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Name cannot be null"));
        }
    }

    @Test
    public void GivenExistingLanguageid_WhenDeleteLanguage_ThenDeletesLanguage() throws Exception {
        // Given
        final Long languageid = 4L;

        // When
        this.languageService.delete(languageid);

        // Then
        final Optional<Language> language = this.languageRepository.findById(languageid);
        assertThat(language.isPresent(), is(Boolean.FALSE));
    }


    @Test
    public void GivenUnexistingLanguageID_WhenDeleteLanguage_ThenReturnsErrorMessage() {
        // Given
        final Long languageid = 4L;

        try {
            // When
            this.languageService.get(languageid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language 4 not found."));
        }
    }

    @Test
    public void WhenListLanguages_ThenReturnsLanguageList() {
        // Given
        final Integer expectedResults = 8;

        // When
        final List<Language> list = this.languageService.list();

        // Then
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(expectedResults)));
    }
}

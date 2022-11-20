package com.example.backtest.controller;

import com.example.backtest.model.Language;
import com.example.backtest.service.LanguageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
@AutoConfigureMockMvc
public class LanguageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LanguageService languageService;

    @Test
    public void GivenRealGetCallAndExistingLanguageID_WhenGetLanguageEndpoint_ThenReturnsLanguage() throws Exception {
        final Long languageid = 1L;

        final Language language = this.getLanguage(languageid);

        assertThat(language, is(notNullValue()));
        assertThat(language.getLanguageid(), is(languageid));
        assertThat(language.getName(), is(notNullValue()));
    }

    @Test
    public void GivenRealListCall_WhenListLanguagesEndpoint_ThenReturnsListLanguages() throws Exception {
        final Integer expectedResults = 7;

        final List<Language> list = this.listLanguage();

        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(expectedResults)));
    }

    @Test
    public void GivenRealPostCallAndLanguageObject_WhenPostLanguageEndpoint_ThenCreatesAndReturnsLanguage() throws Exception {
        // Given
        final String name = "Indonesian";

        Language language = new Language();
        language.setName(name);

        // When
        final Language createdLanguage = this.createLanguage(language);

        //Then
        assertThat(createdLanguage, is(notNullValue()));
        assertThat(createdLanguage.getLanguageid(), is(notNullValue()));
        assertThat(createdLanguage.getName(), is(equalTo(name)));
    }

    @Test
    public void GivenRealDeleteCallAndExistingLanguageID_WhenDeleteLanguagesEndpoint_ThenDeleteLanguage() throws Exception {
        // Given
        final Long languageid = 6L;

        // When
        this.deleteLanguage(languageid);

        try {
            this.languageService.get(languageid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Language 6 not found."));
        }
    }

    @Test
    public void GivenRealUpdateCallAndLanguageObject_WhenPutLanguageEndpoint_ThenCreatesAndReturnsLanguage() throws Exception {
        // Given
        final Long languageid = 2L;
        final String name = "test_language";

        Language language = this.getLanguage(languageid);
        language.setName(name);

        // When
        final Language updatedLanguage = this.updateLanguage(languageid, language);

        //Then
        assertThat(updatedLanguage, is(notNullValue()));
        assertThat(updatedLanguage.getLanguageid(), is(equalTo(languageid)));
    }



    private Language getLanguage(Long languageid) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/backtest/v1/languages/{languageid}", languageid))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return this.objectMapper.readValue(response, Language.class);
    }

    private List<Language> listLanguage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/backtest/v1/languages"))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return List.of(this.objectMapper.readValue(response, Language[].class));
    }

    private Language createLanguage(Language language) throws Exception {
        MvcResult result = mockMvc.perform(post("/backtest/v1/languages")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(language)))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, Language.class);
    }

    private Language updateLanguage(Long languageid, Language language) throws Exception {
        MvcResult result = mockMvc.perform(put("/backtest/v1/languages/{movieid}", languageid)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(language)))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, Language.class);
    }

    private void deleteLanguage(Long languageid) throws Exception {
        this.mockMvc.perform(delete("/backtest/v1/languages/{languageid}", languageid))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}

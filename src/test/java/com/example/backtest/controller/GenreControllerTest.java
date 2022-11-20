package com.example.backtest.controller;

import com.example.backtest.model.Genre;
import com.example.backtest.service.GenreService;
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
public class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GenreService genreService;

    @Test
    public void GivenRealGetCallAndExistingGenreID_WhenGetGenreEndpoint_ThenReturnsGenre() throws Exception {
        final Long genreid = 1L;

        final Genre genre = this.getGenre(genreid);

        assertThat(genre, is(notNullValue()));
        assertThat(genre.getGenreid(), is(genreid));
        assertThat(genre.getName(), is(notNullValue()));
    }

    @Test
    public void GivenRealListCall_WhenListGenresEndpoint_ThenReturnsListGenres() throws Exception {
        final Integer expectedResults = 11;

        final List<Genre> list = this.listGenre();

        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(expectedResults)));
    }

    @Test
    public void GivenRealPostCallAndGenreObject_WhenPostGenreEndpoint_ThenCreatesAndReturnsGenre() throws Exception {
        // Given
        final String name = "Indian";

        Genre genre = new Genre();
        genre.setName(name);

        // When
        final Genre createdGenre = this.createGenre(genre);

        //Then
        assertThat(createdGenre, is(notNullValue()));
        assertThat(createdGenre.getGenreid(), is(notNullValue()));
        assertThat(createdGenre.getName(), is(equalTo(name)));
    }

    @Test
    public void GivenRealDeleteCallAndExistingGenreID_WhenDeleteGenresEndpoint_ThenDeleteGenre() throws Exception {
        // Given
        final Long genreid = 6L;

        // When
        this.deleteGenre(genreid);

        try {
            this.genreService.get(genreid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre 6 not found."));
        }
    }

    @Test
    public void GivenRealUpdateCallAndGenreObject_WhenPutGenreEndpoint_ThenCreatesAndReturnsGenre() throws Exception {
        // Given
        final Long genreid = 2L;
        final String name = "test_genre";

        Genre genre = this.getGenre(genreid);
        genre.setName(name);

        // When
        final Genre updatedGenre = this.updateGenre(genreid, genre);

        //Then
        assertThat(updatedGenre, is(notNullValue()));
        assertThat(updatedGenre.getGenreid(), is(equalTo(genreid)));
    }

    private Genre getGenre(Long genreid) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/backtest/v1/genres/{genreid}", genreid))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return this.objectMapper.readValue(response, Genre.class);
    }

    private List<Genre> listGenre() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/backtest/v1/genres"))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return List.of(this.objectMapper.readValue(response, Genre[].class));
    }

    private Genre createGenre(Genre genre) throws Exception {
        MvcResult result = mockMvc.perform(post("/backtest/v1/genres")
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, Genre.class);
    }

    private Genre updateGenre(Long genreid, Genre genre) throws Exception {
        MvcResult result = mockMvc.perform(put("/backtest/v1/genres/{movieid}", genreid)
                        .contentType("application/json")
                        .content(this.objectMapper.writeValueAsString(genre)))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, Genre.class);
    }

    private void deleteGenre(Long genreid) throws Exception {
        this.mockMvc.perform(delete("/backtest/v1/genres/{genreid}", genreid))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}

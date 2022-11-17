package com.example.backtest.service;

import com.example.backtest.model.Genre;
import com.example.backtest.repository.GenreRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    @Autowired
    private GenreRepository genreRepository;

    @Test
    public void GivenExistingGenreID_WhenGetGenre_ThenReturnsGenre() throws Exception {
        // Given
        final Long genreid = 1L;

        // When
        final Genre genre = this.genreService.get(genreid);

        // Then
        assertThat(genre, is(notNullValue()));
        assertThat(genre.getGenreid(), is(genreid));
        assertThat(genre.getName(), is(notNullValue()));
    }

    @Test
    public void GivenUnexistingGenreID_WhenGetGenre_ThenReturnsErrorMessage() {
        // Given
        final Long genreid = -2L;
        try {
            // When
            this.genreService.get(genreid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre -2 not found."));
        }
    }

    @Test
    public void GivenNullGenreID_WhenGetGenre_ThenReturnsErrorMessage() {
        // Given
        final Long genreid = null;

        try {
            // When
            this.genreService.get(genreid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("genreid cannot be null"));
        }
    }

    @Test
    public void GivenNonExistingGenreObject_WhenCreateGenre_ThenCreatesGenre() throws Exception {
        // Given
        final String newGenre = "Crime";

        Genre genre = new Genre();
        genre.setName(newGenre);

        // When
        final Genre genreid = this.genreService.create(genre);

        // Then
        assertThat(genreid.getGenreid(), is(notNullValue()));
        assertThat(genre.getName(), is(equalTo(newGenre)));
    }

    @Test
    public void GivenExistingName_WhenCreateGenre_ThenShouldFail() {
        // Given
        final String newGenre = "Drama";

        Genre genre = new Genre();
        genre.setName(newGenre);

        try {
            // When
            this.genreService.create(genre);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre with name 'Drama' already exists"));
        }
    }

    @Test
    public void GivenNullGenre_WhenCreateGenre_ThenReturnsErrorMessage() {
        // Given
        final Genre genre = null;

        try {
            // When
            this.genreService.create(genre);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre cannot be null"));
        }
    }

    @Test
    public void GivenNullName_WhenCreateGenre_ThenReturnsErrorMessage() {
        // Given
        final Genre genre = new Genre();

        try {
            // When
            this.genreService.create(genre);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Name cannot be null"));
        }
    }

    @Test
    public void GivenRealGenreidAndNonExistingGenre_WhenUpdateGenre_ThenShouldUpdate() throws Exception {
        // Given
        final Long genreid = 1L;
        final String newName = "SuperHero";

        Genre updatedGenre = new Genre();
        updatedGenre.setName(newName);

        // When
        Genre genreDB = this.genreService.update(genreid,updatedGenre);

        // Then
        assertThat(genreDB.getGenreid(), is(equalTo(genreid)));
        assertThat(genreDB.getName(), is(equalTo(newName)));
    }

    @Test
    public void GivenRealGenreidAndExistingName_WhenUpdateGenre_ThenShouldFail() {
        // Given
        final Long genreid = 2L;

        Genre genre = new Genre();
        genre.setName("Animated");
        try {
            // When
            this.genreService.update(genreid, genre);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre with name Animated already exists"));
        }
    }

    @Test
    public void GivenNullGenre_WhenUpdateGenre_ThenReturnsErrorMessage() {
        // Given
        final Long genreid = 2L;
        final Genre genre = null;

        try {
            // When
            this.genreService.update(genreid, genre);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre cannot be null"));
        }
    }

    @Test
    public void GivenNullName_WhenUpdateGenre_ThenReturnsErrorMessage() {
        // Given
        final Long genreid = 2L;
        final Genre genre = new Genre();

        try {
            // When
            this.genreService.update(genreid, genre);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Name cannot be null"));
        }
    }

    @Test
    public void GivenExistingGenreid_WhenDeleteGenre_ThenDeletesGenre() throws Exception {
        // Given
        final Long genreid = 4L;

        // When
        this.genreService.delete(genreid);

        // Then
        final Optional<Genre> genre = this.genreRepository.findById(genreid);
        assertThat(genre.isPresent(), is(Boolean.FALSE));
    }


    @Test
    public void GivenUnexistingGenreID_WhenDeleteGenre_ThenReturnsErrorMessage() {
        // Given
        final Long genreid = 4L;

        try {
            // When
            this.genreService.get(genreid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Genre 4 not found."));
        }
    }

    @Test
    public void WhenListGenres_ThenReturnsGenreList() {
        // Given
        final Integer expectedResults = 11;

        // When
        final List<Genre> list = this.genreService.list();

        // Then
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(expectedResults)));
    }
}

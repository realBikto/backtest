package com.example.backtest.repository;

import com.example.backtest.model.Genre;
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
public class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    @Test(expected = DataIntegrityViolationException.class)
    public void GivenExistingName_WhenCreateGenreWithSameName_ThenShouldFail() {
        // Given
        final String name = "Animated";

        Genre genre = new Genre();

        genre.setName(name);
        // Then
        this.genreRepository.saveAndFlush(genre);
    }
}

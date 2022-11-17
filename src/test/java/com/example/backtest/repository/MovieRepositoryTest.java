package com.example.backtest.repository;

import com.example.backtest.model.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.data.domain.Pageable;

import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;


@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:db-test.properties")
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void GivenExistingTitle_WhenExists_ThenShouldBeTrue() {
        // Given
        final String title = "El padrino";
        // When
        final Boolean exists = this.movieRepository.exists(title);
        // Then
        assertThat(exists, is(Boolean.TRUE));
    }

    @Test
    public void GivenNonExistingTitle_WhenNoExists_ThenShouldBeFalse() {
        // Given
        final String title = "La madrina";
        // When
        final Boolean exists = this.movieRepository.exists(title);
        // Then
        assertThat(exists, is(Boolean.FALSE));
    }

    @Test
    public void GivenOneExistingTitle_WhenListPageable_ThenShouldReturnExactOneElement() {
        // Given
        final String title = "El señor";
        final Pageable pageable = PageRequest.of(0, 4);
        final Integer maxResults = 100;

        final Long expectedElements = 1L;

        // When
        final Page page = this.movieRepository.list(title,pageable,maxResults);

        // Then
        assertThat(page, is(not(nullValue())));
        assertThat(page.getTotalElements(), is(equalTo(expectedElements)));
    }

    @Test
    public void GivenNoneExistingTitle_WhenFilterPageable_ThenShouldReturnNoElements() {
        // Given
        final String title = "La madrina";
        final Pageable pageable = PageRequest.of(0, 4);
        final Integer maxResults = 100;

        // When
        final Page page = this.movieRepository.list(title,pageable,maxResults);

        // Then
        assertThat(page.getContent(), is(emptyList()));
    }

    @Test
    public void GivenNoTitleDefaultSize_WhenListPageable_ThenMaxresultsPerPageShouldBeLimited() {
        // Given
        final Pageable pageable = PageRequest.of(0, 4);
        final Integer maxResults = 100;

        final Integer expectedSize = 4;

        // When
        final Page page = this.movieRepository.list(null,pageable,maxResults);

        // Then
        assertThat(page.getSize(), is(equalTo(expectedSize)));
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void GivenExistingTitle_WhenCreateMovieWithSameTitle_ThenShouldFail() {
        // Given
        final String title = "La lista de Schindler";

        Movie movie = new Movie();

        movie.setTitle(title);
        // Then
        this.movieRepository.saveAndFlush(movie);
    }
}

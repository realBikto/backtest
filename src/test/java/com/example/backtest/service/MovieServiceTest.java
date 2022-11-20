package com.example.backtest.service;

import com.example.backtest.model.Genre;
import com.example.backtest.model.Language;
import com.example.backtest.model.Movie;
import com.example.backtest.repository.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
public class MovieServiceTest {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    public void GivenExistingMovieID_WhenGetMovie_ThenReturnsMovie() throws Exception {
        // Given
        final Long movieid = 1L;

        // When
        final Movie movie = this.movieService.get(movieid);

        // Then
        assertThat(movie, is(notNullValue()));
        assertThat(movie.getMovieid(), is(movieid));
        assertThat(movie.getTitle(), is(notNullValue()));
    }

    @Test
    public void GivenUnexistingMovieID_WhenGetMovie_ThenReturnsErrorMessage() {
        // Given
        final Long movieid = -2L;
        try {
            // When
            this.movieService.get(movieid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie -2 not found."));
        }
    }

    @Test
    public void GivenNullMovieID_WhenGetMovie_ThenReturnsErrorMessage() {
        // Given
        final Long movieid = null;

        try {
            // When
            this.movieService.get(movieid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("movieid cannot be null"));
        }
    }

    @Test
    public void WhenListMovies_ThenReturnsMovieList() {
        // Given
        final Integer expectedResults = 5;

        // When
        final List<Movie> list = this.movieService.list();

        // Then
        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(expectedResults)));
    }

    @Test
    public void GivenNonExistingMovieObject_WhenCreateMovie_ThenCreatesMovie() throws Exception {
        // Given
        Language language = new Language();
        language.setLanguageid(1L);
        language.setName("Spanish");

        Genre genre = new Genre();
        genre.setName("Drama");
        genre.setGenreid(5L);

        Movie movie = new Movie();
        movie.setTitle("Pulp Fiction");
        movie.setOriginaltitle("Pulp Fiction");
        movie.setYear("1994");
        movie.setDirector("Quentin Tarantino");
        movie.setActors(Arrays.asList("John Travolta","Uma Thurman","Samuel L. Jackson"));
        movie.setImage("https://pics.filmaffinity.com/Pulp_Fiction-210382116-mmed.jpg");
        movie.setCreatedby("admin");
        movie.setModifiedby("admin");
        movie.setLanguage(language);
        movie.setGenre(genre);

        // When
        final Movie moviedb = this.movieService.create(movie);

        // Then
        assertThat(moviedb.getMovieid(), is(notNullValue()));
        assertThat(movie.getTitle(), is(equalTo(moviedb.getTitle())));
    }

    @Test
    public void GivenExistingTitle_WhenCreateMovie_ThenShouldFail() {
        // Given
        Movie movie = new Movie();
        movie.setTitle("El padrino");

        try {
            // When
            this.movieService.create(movie);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie with title 'El padrino' already exists"));
        }
    }

    @Test
    public void GivenNullMovie_WhenCreateMovie_ThenReturnsErrorMessage() {
        // Given
        final Movie movie = null;

        try {
            // When
            this.movieService.create(movie);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie cannot be null"));
        }
    }

    @Test
    public void GivenNullTitle_WhenCreateMovie_ThenReturnsErrorMessage() {
        // Given
        final Movie movie = new Movie();

        try {
            // When
            this.movieService.create(movie);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Title cannot be null"));
        }
    }

    @Test
    public void GivenRealMovieidAndNonExistingMovie_WhenUpdateMovie_ThenShouldUpdate() throws Exception {
        // Given
        final Long movieid = 1L;

        Language newLanguage = new Language();
        newLanguage.setLanguageid(1L);
        newLanguage.setName("Spanish");

        Genre newGenre = new Genre();
        newGenre.setName("Drama");
        newGenre.setGenreid(5L);

        final String newTitle = "El padrino (parte II)";
        final String newOriginaltitle = "The Godfather Part II";
        final String newYear = "1974";
        final String newDirector = "Francis Ford Coppola";
        final List<String> newActors = Arrays.asList("Al Pacino","Robert De Niro","Robert Duvall");
        final String newImage = "https://pics.filmaffinity.com/El_padrino_Parte_II-124238415-large.jpg";
        final String newModifiedby = "admin2";

        Movie updatedMovie = new Movie();
        updatedMovie.setTitle(newTitle);
        updatedMovie.setOriginaltitle(newOriginaltitle);
        updatedMovie.setYear(newYear);
        updatedMovie.setLanguage(newLanguage);
        updatedMovie.setGenre(newGenre);
        updatedMovie.setDirector(newDirector);
        updatedMovie.setActors(newActors);
        updatedMovie.setImage(newImage);
        updatedMovie.setModifiedby(newModifiedby);

        // When
        Movie movieDB = this.movieService.update(movieid,updatedMovie);

        // Then
        assertThat(movieDB.getMovieid(), is(equalTo(movieid)));
        assertThat(movieDB.getTitle(), is(equalTo(newTitle)));
        assertThat(movieDB.getOriginaltitle(), is(equalTo(newOriginaltitle)));
        assertThat(movieDB.getYear(), is(equalTo(newYear)));
        assertThat(movieDB.getLanguage().getLanguageid(), is(equalTo(newLanguage.getLanguageid())));
        assertThat(movieDB.getGenre().getGenreid(), is(equalTo(newGenre.getGenreid())));
        assertThat(movieDB.getDirector(), is(equalTo(newDirector)));
        assertThat(movieDB.getActors(), is(equalTo(newActors)));
        assertThat(movieDB.getImage(), is(equalTo(newImage)));
        assertThat(movieDB.getModifiedby(), is(equalTo(newModifiedby)));

    }

    @Test
    public void GivenRealMovieidAndExistingTitle_WhenUpdateMovie_ThenShouldFail() {
        // Given
        final Long movieid = 2L;

        Movie movie = new Movie();
        movie.setTitle("El padrino");
        try {
            // When
            this.movieService.update(movieid, movie);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie with title El padrino already exists"));
        }
    }

    @Test
    public void GivenNullMovie_WhenUpdateMovie_ThenReturnsErrorMessage() {
        // Given
        final Long movieid = 2L;
        final Movie movie = null;

        try {
            // When
            this.movieService.update(movieid, movie);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie cannot be null"));
        }
    }

    @Test
    public void GivenNullTitle_WhenUpdateMovie_ThenReturnsErrorMessage() {
        // Given
        final Long movieid = 2L;
        final Movie movie = new Movie();

        try {
            // When
            this.movieService.update(movieid, movie);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Title cannot be null"));
        }
    }

    @Test
    public void GivenExistingMovieid_WhenDeleteMovie_ThenDeletesMovie() throws Exception {
        // Given
        final Long movieid = 2L;

        // When
        this.movieService.delete(movieid);

        // Then
        final Optional<Movie> movie = this.movieRepository.findById(movieid);
        assertThat(movie.isPresent(), is(Boolean.FALSE));
    }


    @Test
    public void GivenUnexistingMovieID_WhenDeleteMovie_ThenReturnsErrorMessage() {
        // Given
        final Long movieid = 2L;

        try {
            // When
            this.movieService.get(movieid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie 2 not found."));
        }
    }

    @Test
    public void GivenPageableButNoTitle_WhenListPage_ReturnsPageMovies(){
        // Given
        final Pageable pageable = PageRequest.of(0, 2);

        final Integer expectedSize = 2;
        final Integer expectedPages = 2;

        // When
        final Page page = this.movieService.list(null,pageable);

        // Then
        assertThat(page.getSize(), is(equalTo(expectedSize)));
        assertThat(page.getTotalPages(), is(equalTo(expectedPages)));
    }

    @Test
    public void GivenPageableAndTitle_WhenListPage_ReturnsPageMovies() throws Exception {
        // Given
        final Pageable pageable = PageRequest.of(0, 2);
        final String filterTitle = "el señor";

        final Integer expectedSize = 2;
        final Integer expectedPages = 1;
        final Movie expectedMovie = this.movieService.get(5L);

        // When
        final Page page = this.movieService.list(filterTitle,pageable);

        // Then
        assertThat(page.getSize(), is(equalTo(expectedSize)));
        assertThat(page.getTotalPages(), is(equalTo(expectedPages)));
        assertThat(page.getContent().get(0).getClass(), is(equalTo(expectedMovie.getClass())));
    }

    @Test
    public void GivenNoPageableAndNoTitle_WhenListPage_ThenReturnsAllMoviesInOnePage(){
        // Given

        final Integer expectedSize = 4;
        final Integer expectedPages = 1;

        // When
        final Page page = this.movieService.list(null,null);

        // Then
        assertThat(page.getSize(), is(equalTo(expectedSize)));
        assertThat(page.getTotalPages(), is(equalTo(expectedPages)));
    }
}

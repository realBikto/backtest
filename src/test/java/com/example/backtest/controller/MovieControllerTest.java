package com.example.backtest.controller;

import com.example.backtest.model.Genre;
import com.example.backtest.model.Language;
import com.example.backtest.model.Movie;
import com.example.backtest.service.MovieService;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:db-test.properties")
@AutoConfigureMockMvc
public class MovieControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MovieService movieService;

    @Test
    public void GivenRealGetCallAndExistingMovieID_WhenGetMovieEndpoint_ThenReturnsMovie() throws Exception {
        final Long movieid = 1L;

        final Movie movie = this.getMovie(movieid);

        assertThat(movie, is(notNullValue()));
        assertThat(movie.getMovieid(), is(movieid));
        assertThat(movie.getTitle(), is(notNullValue()));
        assertThat(movie.getCreatedby(), is(notNullValue()));
        assertThat(movie.getCreatedat(), is(notNullValue()));
        assertThat(movie.getModifiedby(), is(notNullValue()));
        assertThat(movie.getModifiedat(), is(notNullValue()));
    }

    @Test
    public void GivenRealListCall_WhenListMoviesEndpoint_ThenReturnsListMovies() throws Exception {
        final Integer expectedResults = 5;

        final List<Movie> list = this.listMovie();

        assertThat(list, is(notNullValue()));
        assertThat(list.size(), is(equalTo(expectedResults)));
    }

    @Test
    public void GivenRealPostCallAndMovieObject_WhenPostMovieEndpoint_ThenCreatesAndReturnsMovie() throws Exception {
        // Given
        final String title = "Forrest Gump";
        final String originaltitle = "Forrest Gump";
        final String year = "1994";
        final String director = "Robert Zemeckis";
        final List<String> actors = Arrays.asList("Tom Hanks","Robin Wright","Gary Sinise");
        final String image = "https://pics.filmaffinity.com/Forrest_Gump-212765827-large.jpg";
        final String createdby = "admin";
        final String modifiedby = "admin";

        Language language = new Language();
        language.setLanguageid(1L);
        language.setName("Spanish");

        Genre genre = new Genre();
        genre.setName("Drama");
        genre.setGenreid(5L);

        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setOriginaltitle(originaltitle);
        movie.setYear(year);
        movie.setDirector(director);
        movie.setActors(actors);
        movie.setImage(image);
        movie.setCreatedby(createdby);
        movie.setModifiedby(modifiedby);
        movie.setLanguage(language);
        movie.setGenre(genre);

        // When
        final Movie createdMovie = this.createMovie(movie);

        //Then
        assertThat(createdMovie, is(notNullValue()));
        assertThat(createdMovie.getMovieid(), is(notNullValue()));
        assertThat(createdMovie.getTitle(), is(equalTo(title)));
        assertThat(createdMovie.getOriginaltitle(), is(equalTo(originaltitle)));
        assertThat(createdMovie.getYear(), is(equalTo(year)));
        assertThat(createdMovie.getLanguage().getLanguageid(), is(equalTo(language.getLanguageid())));
        assertThat(createdMovie.getGenre().getGenreid(), is(equalTo(genre.getGenreid())));
        assertThat(createdMovie.getDirector(), is(equalTo(director)));
        assertThat(createdMovie.getActors(), is(equalTo(actors)));
        assertThat(createdMovie.getImage(), is(equalTo(image)));
        assertThat(createdMovie.getCreatedby(), is(equalTo(createdby)));
        assertThat(createdMovie.getModifiedby(), is(equalTo(modifiedby)));
    }

    @Test
    public void GivenRealDeleteCallAndExistingMovieID_WhenDeleteMoviesEndpoint_ThenDeleteMovie() throws Exception {
        // Given
        final Long movieid = 6L;

        // When
        this.deleteMovie(movieid);

        try {
            this.movieService.get(movieid);
        } catch (Exception e) {
            // Then
            assertThat(e.getMessage(), is("Movie 6 not found."));
        }
    }

    @Test
    public void GivenRealUpdateCallAndMovieObject_WhenPutMovieEndpoint_ThenCreatesAndReturnsMovie() throws Exception {
        // Given
        final Long movieid = 2L;
        final List<String> actors = Arrays.asList("Marlon Brando","Al Pacino","James Caan","Richard S. Castellano",
                "Robert Duvall","Sterling Hayden","John Marley","Richard Conte","Al Lettieri");
        final String modifiedby = "tester_user";

        Movie movie = this.getMovie(movieid);
        movie.setActors(actors);
        movie.setModifiedby(modifiedby);

        // When
        final Movie updatedMovie = this.updateMovie(movieid, movie);

        //Then
        assertThat(updatedMovie, is(notNullValue()));
        assertThat(updatedMovie.getMovieid(), is(equalTo(movieid)));
        assertThat(updatedMovie.getActors(), is(equalTo(actors)));
        assertThat(updatedMovie.getModifiedby(), is(equalTo(modifiedby)));
    }

    private Movie getMovie(Long movieid) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/backtest/v1/movies/{movieid}", movieid))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return this.objectMapper.readValue(response, Movie.class);
    }

    private List<Movie> listMovie() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/backtest/v1/movies"))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return List.of(this.objectMapper.readValue(response, Movie[].class));
    }

    private Movie createMovie(Movie movie) throws Exception {
        MvcResult result = mockMvc.perform(post("/backtest/v1/movies")
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, Movie.class);
    }

    private Movie updateMovie(Long movieid, Movie movie) throws Exception {
        MvcResult result = mockMvc.perform(put("/backtest/v1/movies/{movieid}", movieid)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(movie)))
                .andExpect(status().isOk())
                .andReturn();

        final String response = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(response, Movie.class);
    }

    private void deleteMovie(Long movieid) throws Exception {
        this.mockMvc.perform(delete("/backtest/v1/movies/{movieid}", movieid))
                .andExpect(status().isNoContent())
                .andReturn();
    }
}

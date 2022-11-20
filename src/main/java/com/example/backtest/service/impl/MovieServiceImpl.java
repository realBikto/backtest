package com.example.backtest.service.impl;

import com.example.backtest.model.Movie;
import com.example.backtest.repository.MovieRepository;
import com.example.backtest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie get(final Long movieid) throws Exception {
        if (movieid == null) {
            throw new Exception("movieid cannot be null");
        }
        return this.movieRepository.findById(movieid).orElseThrow(() -> new Exception("Movie " + movieid + " not found."));
    }

    @Override
    public Movie create(final Movie movie) throws Exception {
        MovieServiceImpl.checkMovieFields(movie);

        try {
            return this.movieRepository.saveAndFlush(movie);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Movie with title '" + movie.getTitle() + "' already exists");
        }
    }

    @Override
    public Movie update(final Long movieid, final Movie movie) throws Exception {
        MovieServiceImpl.checkMovieFields(movie);

        Movie movieDB = this.get(movieid);

        try {
            movieDB.setTitle(movie.getTitle());
            movieDB.setOriginaltitle(movie.getOriginaltitle());
            movieDB.setYear(movie.getYear());
            movieDB.setLanguage(movie.getLanguage());
            movieDB.setGenre(movie.getGenre());
            movieDB.setDirector(movie.getDirector());
            movieDB.setActors(movie.getActors());
            movieDB.setImage(movie.getImage());
            movieDB.setModifiedby(movie.getModifiedby());
            return this.movieRepository.saveAndFlush(movieDB);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Movie with title " + movie.getTitle() + " already exists");
        }
    }

    @Override
    public void delete(final Long movieid) throws Exception {
        if (movieid == null) {
            throw new Exception("movieid cannot be null");
        }
        this.movieRepository.deleteById(movieid);
    }

    @Override
    public List<Movie> list() {
        return this.movieRepository.findAll();
    }

    @Override
    public Page<Movie> list(final String title, final Pageable pageable) {
        final Integer maxResults = 100;
        return this.movieRepository.list(title, pageable, maxResults);
    }

    private static void checkMovieFields(Movie movie) throws Exception {
        if (movie == null) {
            throw new Exception("Movie cannot be null");
        }
        if (movie.getTitle() == null) {
            throw new Exception("Title cannot be null");
        }
    }
}

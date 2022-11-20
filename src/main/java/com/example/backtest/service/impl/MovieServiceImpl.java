package com.example.backtest.service.impl;

import com.example.backtest.exception.CustomBacktestException;
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
    public Movie get(final Long movieid) throws CustomBacktestException {
        if (movieid == null) {
            throw new CustomBacktestException("movieid cannot be null", "400");
        }
        return this.movieRepository.findById(movieid).orElseThrow(() -> new CustomBacktestException("Movie " + movieid + " not found.", "404"));
    }

    @Override
    public Movie create(final Movie movie) throws CustomBacktestException {
        MovieServiceImpl.checkMovieFields(movie);

        try {
            return this.movieRepository.saveAndFlush(movie);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomBacktestException("Movie with title '" + movie.getTitle() + "' already exists", "409");
        }
    }

    @Override
    public Movie update(final Long movieid, final Movie movie) throws CustomBacktestException {
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
            throw new CustomBacktestException("Movie with title " + movie.getTitle() + " already exists", "409");
        }
    }

    @Override
    public void delete(final Long movieid) throws CustomBacktestException {
        if (movieid == null) {
            throw new CustomBacktestException("movieid cannot be null", "400");
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

    private static void checkMovieFields(Movie movie) throws CustomBacktestException {
        if (movie == null) {
            throw new CustomBacktestException("Movie cannot be null", "400");
        }
        if (movie.getTitle() == null) {
            throw new CustomBacktestException("Title cannot be null", "400");
        }
    }
}

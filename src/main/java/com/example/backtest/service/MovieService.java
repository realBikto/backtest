package com.example.backtest.service;

import com.example.backtest.model.MovieModel;

import java.util.List;

public interface MovieService {

    boolean createMovie(MovieModel movie);
    MovieModel getMovieById(int movieid);
    boolean updateMovieById(int movieid, MovieModel movie);
    boolean deleteMovieById(int moviedid);
    List<MovieModel> getAllMovies();

}

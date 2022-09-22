package com.example.backtest.repository;

import com.example.backtest.model.MovieModel;

import java.util.List;

public interface MovieRepository {
    boolean save(MovieModel movie);
    boolean update(int movieid, MovieModel movie);
    List<MovieModel> findAll();
    MovieModel findById(int movieid);
    boolean deleteById(int movieid);
}

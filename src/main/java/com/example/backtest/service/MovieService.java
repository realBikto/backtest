package com.example.backtest.service;

import com.example.backtest.exception.CustomBacktestException;
import com.example.backtest.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    Movie create(Movie movie) throws CustomBacktestException;
    Movie get(Long movieid) throws CustomBacktestException;
    Movie update(Long movieid, Movie movie) throws CustomBacktestException;
    void delete(Long movieid) throws CustomBacktestException;
    List<Movie> list();
    Page<Movie> list(String title, Pageable pageable);
}

package com.example.backtest.service;

import com.example.backtest.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MovieService {

    Movie create(Movie movie) throws Exception;
    Movie get(Long movieid) throws Exception;
    Movie update(Long movieid, Movie movie) throws Exception;
    void delete(Long movieid) throws Exception;
    List<Movie> list();
    Page<Movie> list(String title, Pageable pageable);
}

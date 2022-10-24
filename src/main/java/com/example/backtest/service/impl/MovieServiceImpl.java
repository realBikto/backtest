package com.example.backtest.service.impl;

import com.example.backtest.model.MovieModel;
import com.example.backtest.repository.MovieRepository;
import com.example.backtest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public boolean createMovie(MovieModel movie){
        return movieRepository.save(movie);
    }
    @Override
    public MovieModel getMovieById(int movieid){
        return movieRepository.findById(movieid);
    }
    @Override
    public boolean updateMovieById(int movieid, MovieModel movie){
        return movieRepository.update(movieid, movie);
    }
    @Override
    public boolean deleteMovieById(int moviedid){
        return movieRepository.deleteById(moviedid);
    }
    @Override
    public List<MovieModel> getAllMovies(){
        return movieRepository.findAll();
    }

}

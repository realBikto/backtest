package com.example.backtest.controller;

import com.example.backtest.model.BaseDBModel;
import com.example.backtest.model.MovieModel;
import com.example.backtest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "backtest/v1/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieModel>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{movieid}")
    public ResponseEntity<MovieModel> getMovieById(@PathVariable int movieid) {
        return ResponseEntity.ok(movieService.getMovieById(movieid));
    }

    @PostMapping
    public ResponseEntity<BaseDBModel> createMovie(@RequestBody MovieModel movieModel) {
        return ResponseEntity.ok(new BaseDBModel(movieService.createMovie(movieModel)));
    }

    @PutMapping("/{movieid}")
    public ResponseEntity<BaseDBModel> updateMovieById(@PathVariable int movieid, @RequestBody MovieModel movieModel){
        if (movieService.getMovieById(movieid) != null){
            return ResponseEntity.ok(new BaseDBModel(movieService.updateMovieById(movieid, movieModel)));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{movieid}")
    public ResponseEntity<BaseDBModel> deleteMovieById(@PathVariable int movieid){
        return ResponseEntity.ok(new BaseDBModel(movieService.deleteMovieById(movieid)));
    }

}

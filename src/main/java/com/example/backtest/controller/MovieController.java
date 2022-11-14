package com.example.backtest.controller;

import com.example.backtest.model.Movie;
import com.example.backtest.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "backtest/v1/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Movie> listMovies() {
        return this.movieService.list();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{movieid}")
    public Movie getMovieById(@PathVariable Long movieid) throws Exception {
        return this.movieService.get(movieid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) throws Exception {
        return this.movieService.create(movie);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{movieid}")
    public Movie updateMovieById(@PathVariable Long movieid, @RequestBody Movie movie) throws Exception {
        return this.movieService.update(movieid, movie);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{movieid}")
    public void deleteMovieById(@PathVariable Long movieid) throws Exception {
        this.movieService.delete(movieid);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/list")
    public Page<Movie> listPaginatedAndSortableOptionalFilter (
            @RequestParam(required = false) String title,
            @PageableDefault(size = 4, sort = {"year", "title"}, direction = Sort.Direction.ASC) final Pageable pageable) {
        return this.movieService.list(title, pageable);
    }
}

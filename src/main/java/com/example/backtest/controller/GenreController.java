package com.example.backtest.controller;

import com.example.backtest.exception.CustomBacktestException;
import com.example.backtest.model.Genre;
import com.example.backtest.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "backtest/v1/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Genre> listGenres() {
        return this.genreService.list();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{genreid}")
    public Genre getGenreById(@PathVariable Long genreid) throws CustomBacktestException {
        return this.genreService.get(genreid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public Genre createGenre(@RequestBody Genre genre) throws CustomBacktestException {
        return this.genreService.create(genre);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{genreid}")
    public Genre updateGenreById(@PathVariable Long genreid, @RequestBody Genre genre) throws CustomBacktestException {
        return this.genreService.update(genreid, genre);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{genreid}")
    public void deleteGenreById(@PathVariable Long genreid) throws CustomBacktestException {
        this.genreService.delete(genreid);
    }
}

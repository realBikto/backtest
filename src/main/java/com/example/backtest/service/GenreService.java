package com.example.backtest.service;

import com.example.backtest.model.Genre;

import java.util.List;

public interface GenreService {

    Genre create(Genre genre) throws Exception;
    Genre get(Long genreid) throws Exception;
    Genre update(Long genreid, Genre genre) throws Exception;
    void delete(Long genreid) throws Exception;
    List<Genre> list();
}

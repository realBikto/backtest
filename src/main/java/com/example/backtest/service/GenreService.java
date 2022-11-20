package com.example.backtest.service;

import com.example.backtest.exception.CustomBacktestException;
import com.example.backtest.model.Genre;

import java.util.List;

public interface GenreService {

    Genre create(Genre genre) throws CustomBacktestException;
    Genre get(Long genreid) throws CustomBacktestException;
    Genre update(Long genreid, Genre genre) throws CustomBacktestException;
    void delete(Long genreid) throws CustomBacktestException;
    List<Genre> list();
}

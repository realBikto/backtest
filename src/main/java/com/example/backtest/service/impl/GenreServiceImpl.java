package com.example.backtest.service.impl;

import com.example.backtest.exception.CustomBacktestException;
import com.example.backtest.model.Genre;
import com.example.backtest.repository.GenreRepository;
import com.example.backtest.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre get(final Long genreid) throws CustomBacktestException {
        if (genreid == null) {
            throw new CustomBacktestException("genreid cannot be null", "400");
        }
        return this.genreRepository.findById(genreid).orElseThrow(() -> new CustomBacktestException("Genre " + genreid + " not found.", "404"));
    }

    @Override
    public Genre create(final Genre genre) throws CustomBacktestException {
        GenreServiceImpl.checkGenreFields(genre);

        try {
            return this.genreRepository.saveAndFlush(genre);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomBacktestException("Genre with name '" + genre.getName() + "' already exists", "409");
        }
    }

    @Override
    public Genre update(final Long genreid, final Genre genre) throws CustomBacktestException {
        GenreServiceImpl.checkGenreFields(genre);

        Genre genreDB = this.get(genreid);

        try {
            genreDB.setName(genre.getName());
            return this.genreRepository.saveAndFlush(genreDB);
        } catch (DataIntegrityViolationException exception) {
            throw new CustomBacktestException("Genre with name " + genre.getName() + " already exists", "409");
        }
    }

    @Override
    public void delete(final Long genreid) throws CustomBacktestException {
        if (genreid == null) {
            throw new CustomBacktestException("genreid cannot be null", "400");
        }
        this.genreRepository.deleteById(genreid);
    }

    @Override
    public List<Genre> list() {
        return this.genreRepository.findAll();
    }

    private static void checkGenreFields(Genre genre) throws CustomBacktestException {
        if (genre == null) {
            throw new CustomBacktestException("Genre cannot be null", "400");
        }
        if (genre.getName() == null) {
            throw new CustomBacktestException("Name cannot be null", "400");
        }
    }
}

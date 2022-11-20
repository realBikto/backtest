package com.example.backtest.service.impl;

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
    public Genre get(final Long genreid) throws Exception {
        if (genreid == null) {
            throw new Exception("genreid cannot be null");
        }
        return this.genreRepository.findById(genreid).orElseThrow(() -> new Exception("Genre " + genreid + " not found."));
    }

    @Override
    public Genre create(final Genre genre) throws Exception {
        GenreServiceImpl.checkGenreFields(genre);

        try {
            return this.genreRepository.saveAndFlush(genre);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Genre with name '" + genre.getName() + "' already exists");
        }
    }

    @Override
    public Genre update(final Long genreid, final Genre genre) throws Exception {
        GenreServiceImpl.checkGenreFields(genre);

        Genre genreDB = this.get(genreid);

        try {
            genreDB.setName(genre.getName());
            return this.genreRepository.saveAndFlush(genreDB);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Genre with name " + genre.getName() + " already exists");
        }
    }

    @Override
    public void delete(final Long genreid) throws Exception {
        if (genreid == null) {
            throw new Exception("genreid cannot be null");
        }
        this.genreRepository.deleteById(genreid);
    }

    @Override
    public List<Genre> list() {
        return this.genreRepository.findAll();
    }

    private static void checkGenreFields(Genre genre) throws Exception {
        if (genre == null) {
            throw new Exception("Genre cannot be null");
        }
        if (genre.getName() == null) {
            throw new Exception("Name cannot be null");
        }
    }
}

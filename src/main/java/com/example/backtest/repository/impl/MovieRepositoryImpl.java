package com.example.backtest.repository.impl;

import com.example.backtest.mapper.MovieMapper;
import com.example.backtest.model.MovieModel;
import com.example.backtest.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public MovieModel findById(int movieid) {
        return jdbcTemplate.queryForObject("select * from public.movie where movieid = " + movieid + ";", new MovieMapper());
    }

    @Override
    public List<MovieModel> findAll() {
        return jdbcTemplate.query("select * from public.movie order by movieid;", new MovieMapper());
    }

    @Override
    public boolean save(MovieModel movie){
        try {
            String sql = String.format("insert into public.movie (title, originaltitle, year, languages," +
                            " genres, directors, actors, image, createdby, createdat, modifiedby, modifiedat)" +
                            " values ('%s','%s','%s', languages, genres, directors, actors, '%s','%s',now(),'%s',now());",
                    movie.getTitle(), movie.getOriginaltitle(), movie.getYear(), movie.getLanguages(),
                    movie.getGenres(), movie.getDirectors(), movie.getActors(), movie.getImage(),
                    movie.getCreatedby(), movie.getModifiedby());
            jdbcTemplate.execute(sql);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean update(int movieid, MovieModel movie) {
        if (movieid > 0) {
            String sql = String.format("update public.movie set title = '%s', originaltitle = '%s', year = '%s'," +
                            " languages = '', genres = '', directors = '', actors = '', image = '%s', modifiedby = '%s'" +
                            ", modifiedat = now() where movieid = %d;",
                    movie.getTitle(), movie.getOriginaltitle(), movie.getYear(), movie.getLanguages(), movie.getGenres(),
                    movie.getDirectors(), movie.getActors(), movie.getImage(), movie.getModifiedby(), movieid);
            jdbcTemplate.execute(sql);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(int movieid) {
        try{
            String sql = String.format("delete from public.movie where movieid = %d;", movieid);
            jdbcTemplate.execute(sql);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}

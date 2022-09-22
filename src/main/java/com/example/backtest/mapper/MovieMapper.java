package com.example.backtest.mapper;

import com.example.backtest.model.MovieModel;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements RowMapper<MovieModel> {

    @Override
    public MovieModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        MovieModel movieModel = new MovieModel();
        movieModel.setMovieid(rs.getLong("movieid"));
        movieModel.setTitle(rs.getString("title"));
        movieModel.setOriginaltitle(rs.getString("originaltitle"));
        movieModel.setYear(rs.getString("year"));
//        movieModel.setLanguages(rs.getObject("languages"));
//        movieModel.setGenres(rs.getInt("genres"));
//        movieModel.setDirectors(rs.getInt("directors"));
//        movieModel.setActors(rs.getInt("actors"));
        movieModel.setImage(rs.getString("image"));
        movieModel.setCreatedby(rs.getString("createdby"));
        movieModel.setCreatedat(rs.getDate("createdat"));
        movieModel.setModifiedby(rs.getString("modifiedby"));
        movieModel.setModifiedat(rs.getDate("modifiedat"));
        return movieModel;
    }
}

package com.example.backtest.model;

import java.util.Date;
import java.util.List;

public class MovieModel {

    private Long movieid;
    private String title;
    private String originaltitle;
    private String year;
    private Integer[] languages;
    private Integer[] genres;
    private List<String> directors;
    private List<String> actors;
    private String image;
    private String createdby;
    private Date createdat;
    private String modifiedby;
    private Date modifiedat;


    //Getters and setters
    public Long getMovieid() {
        return movieid;
    }

    public void setMovieid(Long movieid) {
        this.movieid = movieid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginaltitle() {
        return originaltitle;
    }

    public void setOriginaltitle(String originaltitle) {
        this.originaltitle = originaltitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer[] getLanguages() {
        return languages;
    }

    public void setLanguages(Integer[] languages) {
        this.languages = languages;
    }

    public Integer[] getGenres() {
        return genres;
    }

    public void setGenres(Integer[] genres) {
        this.genres = genres;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
    }

    public String getModifiedby() {
        return modifiedby;
    }

    public void setModifiedby(String modifiedby) {
        this.modifiedby = modifiedby;
    }

    public Date getModifiedat() {
        return modifiedat;
    }

    public void setModifiedat(Date modifiedat) {
        this.modifiedat = modifiedat;
    }
}

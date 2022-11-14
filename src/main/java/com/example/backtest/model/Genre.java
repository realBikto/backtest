package com.example.backtest.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "genre")
public class Genre implements Serializable {

    private static final long serialVersionUID = -5053267532206076784L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreid;
    private String name;

    // Getters and Setters
    public Long getGenreid() {
        return genreid;
    }

    public void setGenreid(Long genreid) {
        this.genreid = genreid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

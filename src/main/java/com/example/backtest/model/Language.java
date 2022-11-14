package com.example.backtest.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "language")
public class Language implements Serializable {

    private static final long serialVersionUID = -4372926357023865214L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long languageid;
    private String name;

    // Getters ans Setters
    public Long getLanguageid() {
        return languageid;
    }

    public void setLanguageid(Long languageid) {
        this.languageid = languageid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

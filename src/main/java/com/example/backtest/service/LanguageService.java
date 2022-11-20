package com.example.backtest.service;

import com.example.backtest.model.Language;
import java.util.List;

public interface LanguageService {

    Language create(Language language) throws Exception;
    Language get(Long languageid) throws Exception;
    Language update(Long languageid, Language language) throws Exception;
    void delete(Long languageid) throws Exception;
    List<Language> list();
}

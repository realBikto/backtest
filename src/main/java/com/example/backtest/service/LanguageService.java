package com.example.backtest.service;

import com.example.backtest.exception.CustomBacktestException;
import com.example.backtest.model.Language;
import java.util.List;

public interface LanguageService {

    Language create(Language language) throws CustomBacktestException;
    Language get(Long languageid) throws CustomBacktestException;
    Language update(Long languageid, Language language) throws CustomBacktestException;
    void delete(Long languageid) throws CustomBacktestException;
    List<Language> list();
}

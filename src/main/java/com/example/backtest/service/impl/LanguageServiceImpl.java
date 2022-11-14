package com.example.backtest.service.impl;

import com.example.backtest.model.Language;
import com.example.backtest.repository.LanguageRepository;
import com.example.backtest.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Language get(final Long languageid) throws Exception {
        if (languageid == null) {
            throw new Exception("languageid cannot be null");
        }
        return this.languageRepository.findById(languageid).orElseThrow(() -> new Exception("Language " + languageid + " not found."));
    }

    @Override
    public Language create(final Language language) throws Exception {
        LanguageServiceImpl.checkLanguageFields(language);

        try {
            return this.languageRepository.saveAndFlush(language);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Language with name '" + language.getName() + "' already exists");
        }
    }

    @Override
    public Language update(final Long languageid, final Language language) throws Exception {
        LanguageServiceImpl.checkLanguageFields(language);

        Language languageDB = this.get(languageid);

        try {
            languageDB.setName(language.getName());
            return this.languageRepository.saveAndFlush(languageDB);
        } catch (DataIntegrityViolationException exception) {
            throw new Exception("Language with name " + language.getName() + " already exists");
        }
    }

    @Override
    public void delete(final Long languageid) throws Exception {
        if (languageid == null) {
            throw new Exception("languageid cannot be null");
        }
        this.languageRepository.deleteById(languageid);
    }

    @Override
    public List<Language> list() {
        return this.languageRepository.findAll();
    }

    private static void checkLanguageFields(Language language) throws Exception {
        if (language == null) {
            throw new Exception("Language cannot be null");
        }
        if (language.getName() == null) {
            throw new Exception("Title cannot be null");
        }
    }
}

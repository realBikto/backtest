package com.example.backtest.controller;

import com.example.backtest.exception.CustomBacktestException;
import com.example.backtest.model.Language;
import com.example.backtest.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "backtest/v1/languages")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Language> listLanguages() {
        return this.languageService.list();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{languageid}")
    public Language getLanguageById(@PathVariable Long languageid) throws CustomBacktestException {
        return this.languageService.get(languageid);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping
    public Language createLanguage(@RequestBody Language language) throws CustomBacktestException {
        return this.languageService.create(language);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{languageid}")
    public Language updateLanguageById(@PathVariable Long languageid, @RequestBody Language language) throws CustomBacktestException {
        return this.languageService.update(languageid, language);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{languageid}")
    public void deleteLanguageById(@PathVariable Long languageid) throws CustomBacktestException {
        this.languageService.delete(languageid);
    }
}

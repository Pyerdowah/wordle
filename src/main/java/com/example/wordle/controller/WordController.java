package com.example.wordle.controller;

import com.example.wordle.dto.WordGuessStatus;
import com.example.wordle.dto.WordRequestedDto;
import com.example.wordle.dto.WordResponseDto;
import com.example.wordle.mapper.WordMapper;
import com.example.wordle.model.Word;
import com.example.wordle.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class WordController {
    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping(path = "/getAllWords")
    public List<WordResponseDto> getAllWords() {
        return wordService.getAllWords();
    }

    @GetMapping(path = "/getWordById/{wordId}")
    public WordResponseDto getWordById(@PathVariable String wordId) {
        return wordService.getWordById(Long.parseLong(wordId));
    }

    @GetMapping(path = "/getRandomWord")
    public WordResponseDto getRandomWord() {
        return wordService.getRandomWord();
    }

    @GetMapping("/validWord/{wordName}")
    public ResponseEntity<?> validWordCheck(@PathVariable String wordName) {
        Object dictionaryInfo = wordService.getDictionaryInfo(wordName);
        if (Objects.equals(dictionaryInfo.toString(), "404 NOT_FOUND")) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/validChar/{letter}")
    public ResponseEntity<?> validCharCheck(@PathVariable String letter) {
        Boolean validChar = wordService.isCharValid(letter);
        return new ResponseEntity<>(validChar ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping("/wordCheck/{correctWord}/{wordName}")
    public ResponseEntity<String[]> wordCheck(@PathVariable String correctWord, @PathVariable String wordName) {
        String[] guessStatusArray = wordService.wordCheck(correctWord, wordName);
        return new ResponseEntity<>(guessStatusArray, HttpStatus.OK);
    }

    @PostMapping("/addNewWord")
    public ResponseEntity<WordResponseDto> addNewWord(@RequestBody WordRequestedDto wordRequestedDto) {
        return new ResponseEntity<>(wordService.addNewWord(wordRequestedDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateWord/{id}")
    public ResponseEntity<WordResponseDto> updateWord(@PathVariable(name = "id") Long wordId, @RequestBody WordRequestedDto wordRequestedDto) {
        return ResponseEntity.ok().body(wordService.updateWord(wordId, wordRequestedDto));
    }

    @DeleteMapping("/deleteWord")
    public ResponseEntity<?> deleteWord(@RequestBody WordRequestedDto wordRequestedDto) {
        wordService.deleteWord(wordRequestedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

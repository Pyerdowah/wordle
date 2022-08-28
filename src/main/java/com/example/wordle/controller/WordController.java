package com.example.wordle.controller;

import com.example.wordle.dto.WordGuessStatus;
import com.example.wordle.dto.WordRequestedDto;
import com.example.wordle.dto.WordResponseDto;
import com.example.wordle.mapper.WordMapper;
import com.example.wordle.model.Word;
import com.example.wordle.service.WordService;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class WordController {
    private final WordService wordService;
    @Autowired
    public WordController(WordService wordService){
        this.wordService = wordService;
    }
    @GetMapping(path = "/getAllWords")
    public List<WordResponseDto> getAllWords(){
        return wordService.getAllWords().stream().map(WordMapper::objectToResponseDto).collect(Collectors.toList());
    }
    @GetMapping(path = "/getRandomWord")
    public WordResponseDto getRandomWord(){
        Word word = wordService.getRandomWord();
        return WordMapper.objectToResponseDto(word);
    }

    @GetMapping("/validWord/{wordName}")
    public ResponseEntity<String> validWordCheck(@PathVariable String wordName){
        Object dictionaryInfo = wordService.getDictionaryInfo(wordName);
        if (dictionaryInfo.toString() != null && dictionaryInfo.toString().contains("phonetic=")){
            return new ResponseEntity<>("valid", HttpStatus.OK);
        }
        return new ResponseEntity<>("inValid", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/validChar/{letter}")
    public ResponseEntity<String> validCharCheck(@PathVariable String letter){
        Boolean validChar = wordService.validCharCheck(letter);
        if (validChar){
            return new ResponseEntity<>("valid", HttpStatus.OK);
        }
        return new ResponseEntity<>("inValid", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/wordCheck/{correctWord}/{wordName}")
    public ResponseEntity<Map<Integer, WordGuessStatus>> wordCheck(@PathVariable String correctWord, @PathVariable String wordName){
        Map<Integer, WordGuessStatus> guessStatusMap = wordService.wordCheck(correctWord, wordName);
        return new ResponseEntity<>(guessStatusMap, HttpStatus.OK);
    }

    @PostMapping("/addNewWord")
    public ResponseEntity<WordResponseDto> addNewWord(@RequestBody WordRequestedDto wordRequestedDto) {
        // convert DTO to entity
        Word wordRequest = WordMapper.requestedDtoToObject(wordRequestedDto);
        Word word = wordService.addNewWord(wordRequest);
        // convert entity to DTO
        WordResponseDto wordResponseDto = WordMapper.objectToResponseDto(word);
        return new ResponseEntity<>(wordResponseDto, HttpStatus.CREATED);
    }
    @PutMapping("/updateWord/{id}")
    public ResponseEntity<WordResponseDto> updateWord(@PathVariable(name = "id") Long wordId, @RequestBody WordRequestedDto wordRequestedDto) {
        // convert DTO to Entity
        Word wordRequest = WordMapper.requestedDtoToObject(wordRequestedDto);
        Word word = wordService.updateWord(wordId, wordRequest);
        // entity to DTO
        WordResponseDto wordResponseDto = WordMapper.objectToResponseDto(word);

        return ResponseEntity.ok().body(wordResponseDto);
    }

    @DeleteMapping("/deleteWord")
    public void deleteWord(@RequestBody WordRequestedDto wordRequestedDto) {
        Word wordRequest = WordMapper.requestedDtoToObject(wordRequestedDto);
        wordService.deleteWord(wordRequest);
    }
}

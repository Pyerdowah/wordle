package com.example.wordle.service;

import com.example.wordle.Constants;
import com.example.wordle.dto.WordGuessStatus;
import com.example.wordle.dto.WordRequestedDto;
import com.example.wordle.dto.WordResponseDto;
import com.example.wordle.mapper.WordMapper;
import com.example.wordle.model.Word;
import com.example.wordle.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WordService {
    private final WordRepository wordRepository;

    @Autowired
    public WordService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public List<WordResponseDto> getAllWords() {
        return wordRepository.findAll().stream()
                .map(WordMapper::objectToResponseDto)
                .collect(Collectors.toList());
    }

    public WordResponseDto addNewWord(WordRequestedDto wordRequestedDto) {
        Word word = WordMapper.requestedDtoToObject(wordRequestedDto);
        Word newWord = wordRepository.findWordByName(word.getWordName()).orElse(null);
        if (newWord != null) throw new IllegalStateException("slowo zajete");
        wordRepository.save(word);
        return WordMapper.objectToResponseDto(word);
    }

    public void deleteWord(WordRequestedDto wordRequestedDto) {
        Word word = WordMapper.requestedDtoToObject(wordRequestedDto);
        wordRepository.deleteById(word.getWordId());
    }

    public Word getWordByWordName(String wordName) {
        Word word = wordRepository.findWordByName(wordName).orElse(null);
        if (word == null) {
            throw new IllegalStateException("nie ma takiego slowa");
        }
        return word;
    }

    public WordResponseDto updateWord(Long wordId, WordRequestedDto wordRequestedDto) {
        Word wordRequest = WordMapper.requestedDtoToObject(wordRequestedDto);
        Word word = wordRepository.findById(wordId).orElse(null);
        if (word == null) {
            throw new IllegalStateException("nie ma takiego slowa");
        }
        Word helper = wordRepository.findWordByName(wordRequest.getWordName()).orElse(null);
        if (helper != null && !Objects.equals(helper.getWordId(), word.getWordId())) {
            throw new IllegalStateException("slowo juz istnieje");
        }
        word.setWordName(wordRequest.getWordName());
        wordRepository.update(word);
        return WordMapper.objectToResponseDto(word);
    }

    public WordResponseDto getRandomWord() {
        Word word = wordRepository.getRandomWord();
        return WordMapper.objectToResponseDto(word);
    }

    public Object getDictionaryInfo(String wordName) throws HttpClientErrorException {
        String dictionaryApiUrl = Constants.DICTIONARY_API_URL + wordName;
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(dictionaryApiUrl, Object.class);
        } catch (HttpClientErrorException e) {
            return HttpStatus.NOT_FOUND;
        }
    }

    public Boolean isCharValid(String letter) {
        return letter.matches(Constants.VALID_LETTERS);
    }

    public String[] wordCheck(String correctWord, String wordName) {
        String[] guessStatusArray = new String[5];
        Map<Integer, Character> wordNameMap = new HashMap<>();
        Map<Integer, Character> correctWordMap = new HashMap<>();
        for (int i = 0; i < Constants.WORD_LENGTH; i++) {
            guessStatusArray[i] = "grey";
            wordNameMap.put(i, wordName.charAt(i));
            correctWordMap.put(i, correctWord.charAt(i));
        }
        for (int i = 0; i < Constants.WORD_LENGTH; i++) {
            if (wordName.charAt(i) == correctWord.charAt(i)) {
                guessStatusArray[i] = "green";
                wordNameMap.remove(i);
                correctWordMap.remove(i);
            }
        }
        for (int i = 0; i < Constants.WORD_LENGTH; i++){
            if (correctWordMap.containsValue(wordNameMap.get(i)) && wordName.charAt(i) != correctWord.charAt(i)) {
                guessStatusArray[i] = "yellow";
                wordNameMap.remove(i);
                correctWordMap.remove(correctWord.indexOf(wordName.charAt(i)));
            }
        }
        return guessStatusArray;
    }
}

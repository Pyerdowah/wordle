package com.example.wordle.service;

import com.example.wordle.dto.WordGuessStatus;
import com.example.wordle.dto.WordResponseDto;
import com.example.wordle.model.User;
import com.example.wordle.model.Word;
import com.example.wordle.repository.UserRepository;
import com.example.wordle.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class WordService {
    private final WordRepository wordRepository;
    @Autowired
    public WordService(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    public List<Word> getAllWords(){
        return wordRepository.findAll();
    }

    public Word addNewWord(Word word){
        Optional<Word> newWord = wordRepository.findWordByName(word.getWordName());
        if (newWord.isPresent()) throw new IllegalStateException("slowo zajete");
        wordRepository.save(word);
        return word;
    }

    public void deleteWord(Word word) {
        wordRepository.deleteById(word.getWordId());
    }

    public Word getWordByWordName(String wordName) {
        Optional<Word> word = wordRepository.findWordByName(wordName);
        if (word.isEmpty()){
            throw new IllegalStateException("nie ma takiego slowa");
        }
        return word.get();
    }

    public Word updateWord(Long wordId, Word wordRequest) {
        Optional<Word> word = wordRepository.findById(wordId);
        if (word.isEmpty()) {
            throw new IllegalStateException("nie ma takiego slowa");
        }
        Optional<Word> helper = wordRepository.findWordByName(wordRequest.getWordName());
        if (helper.isPresent() && !Objects.equals(helper.get().getWordId(), word.get().getWordId())) {
            throw new IllegalStateException("slowo juz istnieje");
        }
        word.get().setWordName(wordRequest.getWordName());
        wordRepository.update(word.get());
        return word.get();
    }

    public Word getRandomWord() {
        return wordRepository.getRandomWord();
    }

    public Object getDictionaryInfo(String wordName) throws HttpClientErrorException {
        String dictionaryApiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/" + wordName;
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.getForObject(dictionaryApiUrl, Object.class);
        }catch (HttpClientErrorException e){
            return HttpStatus.NOT_FOUND;
        }
    }

    public Boolean validCharCheck(String letter) {
        boolean flag = true;
        String expression= "[a-zA-Z]";
        if(!letter.matches(expression)){
            flag=false;
        }
        return flag;
    }

    public Map<Integer, WordGuessStatus> wordCheck(String correctWord, String wordName) {
        Map<Integer, WordGuessStatus> guessStatusMap = new HashMap<>();
        Map<Integer, Character> wordNameMap = new HashMap<>();
        Map<Integer, Character> correctWordMap = new HashMap<>();
        for(int i = 0; i < wordName.length(); i++){
            guessStatusMap.put(i, WordGuessStatus.GREY);
            wordNameMap.put(i, wordName.charAt(i));
            correctWordMap.put(i, correctWord.charAt(i));
        }
        for(int i = 0; i < wordName.length(); i++){
            if (correctWordMap.containsValue(wordNameMap.get(i)) && wordName.charAt(i) == correctWord.charAt(i)){
                guessStatusMap.put(i, WordGuessStatus.GREEN);
                wordNameMap.remove(i);
                correctWordMap.remove(i);
            }
        }
        for(int i = 0; i < wordName.length(); i++) {
            if (correctWordMap.containsValue(wordNameMap.get(i)) && wordName.charAt(i) != correctWord.charAt(i)) {
                guessStatusMap.put(i, WordGuessStatus.YELLOW);
                wordNameMap.remove(wordName.indexOf(correctWord.charAt(i)));
                correctWordMap.remove(i);
            }
        }
        return guessStatusMap;
    }
}

package com.example.wordle.service;

import com.example.wordle.model.User;
import com.example.wordle.model.Word;
import com.example.wordle.repository.UserRepository;
import com.example.wordle.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
}

package com.example.wordle.mapper;

import com.example.wordle.dto.WordRequestedDto;
import com.example.wordle.dto.WordResponseDto;
import com.example.wordle.model.Word;

public class WordMapper {
    public static Word requestedDtoToObject(WordRequestedDto wordRequestedDto) {
        return Word.builder()
                .wordId(wordRequestedDto.getWordId())
                .wordName(wordRequestedDto.getWordName())
                .build();
    }

    public static WordResponseDto objectToResponseDto(Word word) {
        return WordResponseDto.builder()
                .wordId(word.getWordId())
                .wordName(word.getWordName())
                .build();
    }
}

package com.example.wordle.dto;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WordResponseDto {
    private Long wordId;
    private String wordName;
    private Map<Integer, WordGuessStatus> guessStatusMap;
}

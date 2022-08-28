package com.example.wordle.dto;

import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WordResponseDto {
    private String wordName;
    private Map<Integer, WordGuessStatus> guessStatusMap;
}

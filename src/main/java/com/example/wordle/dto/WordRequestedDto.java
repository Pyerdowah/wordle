package com.example.wordle.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WordRequestedDto {
    private Long wordId;
    private String wordName;
}

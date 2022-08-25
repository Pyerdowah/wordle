package com.example.wordle.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WordResponseDto {
    private String wordName;
}

package com.example.wordle.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WordRequestedDto {
    private Long wordId;
    private String wordName;
}

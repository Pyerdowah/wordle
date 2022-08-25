package com.example.wordle.dto;

import com.example.wordle.model.User;
import com.example.wordle.model.Word;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StatisticResponseDto {
    private int numberOfTries;
    private User user;
    private Word correctWord;
}

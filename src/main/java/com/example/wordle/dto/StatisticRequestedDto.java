package com.example.wordle.dto;

import com.example.wordle.model.User;
import com.example.wordle.model.Word;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StatisticRequestedDto {
    private Long statisticId;
    private int numberOfTries;
    private User user;
    private Word correctWord;
}

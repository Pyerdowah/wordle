package com.example.wordle.mapper;

import com.example.wordle.dto.StatisticRequestedDto;
import com.example.wordle.dto.StatisticResponseDto;
import com.example.wordle.dto.UserRequestedDto;
import com.example.wordle.dto.UserResponseDto;
import com.example.wordle.model.Statistic;
import com.example.wordle.model.User;

public class StatisticMapper {
    public static Statistic requestedDtoToObject(StatisticRequestedDto statisticRequestedDto){
        return Statistic.builder()
                .statisticId(statisticRequestedDto.getStatisticId())
                .numberOfTries(statisticRequestedDto.getNumberOfTries())
                .user(statisticRequestedDto.getUser())
                .correctWord(statisticRequestedDto.getCorrectWord())
                .build();
    }
    public static StatisticResponseDto objectToResponseDto(Statistic statistic){
        return StatisticResponseDto.builder()
                .numberOfTries(statistic.getNumberOfTries())
                .user(statistic.getUser())
                .correctWord(statistic.getCorrectWord())
                .build();
    }
}

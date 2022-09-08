package com.example.wordle.mapper;

import com.example.wordle.dto.StatisticRequestedDto;
import com.example.wordle.dto.StatisticResponseDto;
import com.example.wordle.model.Statistic;

public class StatisticMapper {
    public static Statistic requestedDtoToObject(StatisticRequestedDto statisticRequestedDto) {
        return Statistic.builder()
                .statisticId(statisticRequestedDto.getStatisticId())
                .numberOfTries(statisticRequestedDto.getNumberOfTries())
                .user(statisticRequestedDto.getUser())
                .correctWord(statisticRequestedDto.getCorrectWord())
                .build();
    }

    public static StatisticResponseDto objectToResponseDto(Statistic statistic) {
        return StatisticResponseDto.builder()
                .statisticId(statistic.getStatisticId())
                .numberOfTries(statistic.getNumberOfTries())
                .user(statistic.getUser())
                .correctWord(statistic.getCorrectWord())
                .build();
    }
}

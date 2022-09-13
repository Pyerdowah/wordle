package com.example.wordle.service;

import com.example.wordle.dto.StatisticRequestedDto;
import com.example.wordle.dto.StatisticResponseDto;
import com.example.wordle.mapper.StatisticMapper;
import com.example.wordle.model.Statistic;
import com.example.wordle.model.User;
import com.example.wordle.model.Word;
import com.example.wordle.repository.StatisticRepository;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


public class StatisticServiceTest {

    @Test
    public void add_new_statistic_to_add() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(1L, 5, user, word);
        StatisticRequestedDto statisticRequestedDto = new StatisticRequestedDto(1L, 5, user, word);
        Statistic statistic = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        //when
        when(statisticRepository.getStatisticByUserAndWord(statisticRequestedDto.getUser().getUserId(),
                statisticRequestedDto.getCorrectWord().getWordId())).thenReturn(null);
        when(statisticRepository.save(any(Statistic.class))).thenReturn(statistic);
        //then
        StatisticResponseDto statisticResponseDtoTest = statisticService.addNewStatistic(statisticRequestedDto);
        assertEquals(statisticResponseDto.getStatisticId(), statisticResponseDtoTest.getStatisticId());
        assertEquals(statisticResponseDto.getNumberOfTries(), statisticResponseDtoTest.getNumberOfTries());
        assertEquals(statisticResponseDto.getUser(), statisticResponseDtoTest.getUser());
        assertEquals(statisticResponseDto.getCorrectWord(), statisticResponseDtoTest.getCorrectWord());
    }
}

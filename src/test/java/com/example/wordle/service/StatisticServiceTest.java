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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;


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

    @Test
    public void add_new_statistic_to_update() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        StatisticService statisticServiceMock = Mockito.mock(StatisticService.class);
        //given
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(1L, 5, user, word);
        StatisticRequestedDto statisticRequestedDto = new StatisticRequestedDto(1L, 5, user, word);
        Statistic statistic = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        //when
        when(statisticRepository.getStatisticByUserAndWord(statisticRequestedDto.getUser().getUserId(),
                statisticRequestedDto.getCorrectWord().getWordId())).thenReturn(statistic);
        when(statisticServiceMock.updateStatistic(statistic.getStatisticId(), statisticRequestedDto)).thenReturn(statisticResponseDto);
        when(statisticRepository.findById(statistic.getStatisticId())).thenReturn(Optional.of(statistic));
        //then
        StatisticResponseDto statisticResponseDtoTest = statisticService.addNewStatistic(statisticRequestedDto);
        assertEquals(statisticResponseDto.getStatisticId(), statisticResponseDtoTest.getStatisticId());
        assertEquals(statisticResponseDto.getNumberOfTries(), statisticResponseDtoTest.getNumberOfTries());
        assertEquals(statisticResponseDto.getUser(), statisticResponseDtoTest.getUser());
        assertEquals(statisticResponseDto.getCorrectWord(), statisticResponseDtoTest.getCorrectWord());
    }

    @Test
    public void get_all_statistics_to_list() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticResponseDto statisticResponseDto1 = new StatisticResponseDto(1L, 5, user, word);
        StatisticResponseDto statisticResponseDto2 = new StatisticResponseDto(2L, 5, user, word);
        StatisticResponseDto statisticResponseDto3 = new StatisticResponseDto(3L, 5, user, word);
        Statistic statistic1 = new Statistic(1L, 5, user, word);
        Statistic statistic2 = new Statistic(2L, 5, user, word);
        Statistic statistic3 = new Statistic(3L, 5, user, word);
        List<StatisticResponseDto> listResponseDto = new ArrayList<>();
        listResponseDto.add(statisticResponseDto1);
        listResponseDto.add(statisticResponseDto2);
        listResponseDto.add(statisticResponseDto3);
        List<Statistic> list = new ArrayList<>();
        list.add(statistic1);
        list.add(statistic2);
        list.add(statistic3);
        //when
        when(statisticRepository.findAll()).thenReturn(list);
        //then
        List<StatisticResponseDto> listResponseTested = statisticService.getAllStatistics();
        for (int i = 0; i < listResponseDto.size(); i++) {
            assertEquals(listResponseTested.get(i).getStatisticId(), listResponseDto.get(i).getStatisticId());
            assertEquals(listResponseTested.get(i).getNumberOfTries(), listResponseDto.get(i).getNumberOfTries());
            assertEquals(listResponseTested.get(i).getUser(), listResponseDto.get(i).getUser());
            assertEquals(listResponseTested.get(i).getCorrectWord(), listResponseDto.get(i).getCorrectWord());
        }
    }

    @Test
    public void get_all_statistics_to_empty_list() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        //when
        when(statisticRepository.findAll()).thenReturn(null);
        //then
        List<StatisticResponseDto> listResponseTested = statisticService.getAllStatistics();
        assertNull(listResponseTested);
    }

    @Test
    public void delete_statistic() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticRequestedDto statisticRequestedDto = new StatisticRequestedDto(1L, 5, user, word);
        Statistic statistic = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        //when
        statisticService.deleteStatistic(statisticRequestedDto);
        //then
        verify(statisticRepository).deleteById(statistic.getStatisticId());
    }

    @Test(expected = IllegalStateException.class)
    public void update_statistic_to_exception() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        Long statisticId = 2L;
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticRequestedDto statisticRequestedDto = new StatisticRequestedDto(1L, 5, user, word);
        //when
        assertThat(statisticRepository.findById(statisticId)).isEmpty();
        //then
        statisticService.updateStatistic(statisticId, statisticRequestedDto);
    }

    @Test
    public void update_statistic_to_update() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        Long statisticId = 1L;
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticRequestedDto statisticRequestedDto = new StatisticRequestedDto(1L, 5, user, word);
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(1L, 5, user, word);
        Statistic statistic = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        //when
        when(statisticRepository.findById(statisticId)).thenReturn(Optional.ofNullable(statistic));
        //then
        StatisticResponseDto statisticResponseDtoTest = statisticService.updateStatistic(statisticId, statisticRequestedDto);
        assertEquals(statisticResponseDto.getStatisticId(), statisticResponseDtoTest.getStatisticId());
        assertEquals(statisticResponseDto.getNumberOfTries(), statisticResponseDtoTest.getNumberOfTries());
        assertEquals(statisticResponseDto.getUser(), statisticResponseDtoTest.getUser());
        assertEquals(statisticResponseDto.getCorrectWord(), statisticResponseDtoTest.getCorrectWord());
    }

    @Test
    public void percentage_of_users_with_number_of_tries() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        int wordId = 1;
        ArrayList<Double> stats = new ArrayList<>();
        stats.add(0.0);
        stats.add(0.0);
        stats.add(20.0);
        stats.add(0.0);
        stats.add(20.0);
        stats.add(40.0);
        stats.add(20.0);
        //when
        when(statisticRepository.numberOfStatisticsOfOneWord(wordId)).thenReturn(5);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 1)).thenReturn(0);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 2)).thenReturn(0);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 3)).thenReturn(1);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 4)).thenReturn(0);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 5)).thenReturn(1);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 6)).thenReturn(2);
        when(statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, 7)).thenReturn(1);
        //then
        ArrayList<Double> statsTest = statisticService.perentageOfUsersWithNumberOfTries(wordId);
        for (int i = 0; i < stats.size(); i++) {
            assertEquals(statsTest.get(i), stats.get(i));
        }
    }

    @Test
    public void get_words_guessed_by_user_to_list() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        Long userId = 1L;
        ArrayList<Long> wordsList = new ArrayList<>();
        wordsList.add(1L);
        wordsList.add(2137L);
        wordsList.add(99L);
        //when
        when(statisticRepository.getWordsGuessedByUser(userId)).thenReturn(wordsList);
        //then
        ArrayList<Long> wordsListTest = statisticService.getWordsGuessedByUser(userId);
        for (int i = 0; i < wordsList.size(); i++) {
            assertEquals(wordsList.get(i), wordsListTest.get(i));
        }
    }

    @Test
    public void get_words_guessed_by_user_to_empty_list() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        Long userId = 1L;
        ArrayList<Long> wordsList = new ArrayList<>();
        //when
        when(statisticRepository.getWordsGuessedByUser(userId)).thenReturn(wordsList);
        //then
        ArrayList<Long> wordsListTest = statisticService.getWordsGuessedByUser(userId);
        for (int i = 0; i < wordsList.size(); i++) {
            assertEquals(wordsList.get(i), wordsListTest.get(i));
        }
    }

    @Test
    public void get_statistic_by_login_and_wordName() {
        StatisticRepository statisticRepository = Mockito.mock(StatisticRepository.class);
        StatisticService statisticService = new StatisticService(statisticRepository);
        //given
        User user = new User(1L, "test", "test");
        Word word = new Word(1L, "test");
        StatisticResponseDto statisticResponseDto = new StatisticResponseDto(1L, 5, user, word);
        Statistic statistic = new Statistic(1L, 5, user, word);
        //when
        when(statisticRepository.getStatisticByUserAndWord(user.getUserId(), word.getWordId())).thenReturn(statistic);
        //then
        StatisticResponseDto statisticResponseDtoTest = statisticService.getStatisticBLoginAndWordName(word.getWordId(), user.getUserId());
        assertEquals(statisticResponseDto.getStatisticId(), statisticResponseDtoTest.getStatisticId());
        assertEquals(statisticResponseDto.getNumberOfTries(), statisticResponseDtoTest.getNumberOfTries());
        assertEquals(statisticResponseDto.getUser(), statisticResponseDtoTest.getUser());
        assertEquals(statisticResponseDto.getCorrectWord(), statisticResponseDtoTest.getCorrectWord());
    }

}

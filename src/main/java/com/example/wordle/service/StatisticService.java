package com.example.wordle.service;

import com.example.wordle.Constants;
import com.example.wordle.dto.StatisticRequestedDto;
import com.example.wordle.dto.StatisticResponseDto;
import com.example.wordle.mapper.StatisticMapper;
import com.example.wordle.model.Statistic;
import com.example.wordle.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class StatisticService {
    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticService(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    public StatisticResponseDto addNewStatistic(StatisticRequestedDto statisticRequestedDto) {
        Statistic statisticRequest = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        Statistic id = statisticRepository
                .getStatisticByUserAndWord(statisticRequest.getUser().getUserId(), statisticRequest.getCorrectWord().getWordId());
        if ( id != null) {
            return updateStatistic(id.getStatisticId(), statisticRequestedDto);
        }
        else {
            Statistic statistic = statisticRepository.save(statisticRequest);
            return StatisticMapper.objectToResponseDto(statistic);
        }
    }

    public List<StatisticResponseDto> getAllStatistics() {
        if (statisticRepository.findAll() == null){
            return null;
        }
        return statisticRepository.findAll().stream()
                .map(StatisticMapper::objectToResponseDto)
                .collect(Collectors.toList());
    }

    public void deleteStatistic(StatisticRequestedDto statisticRequestedDto) {
        Statistic statistic = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        statisticRepository.deleteById(statistic.getStatisticId());
    }

    public StatisticResponseDto updateStatistic(Long statisticId, StatisticRequestedDto statisticRequestedDto) {
        Statistic statisticRequest = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        Statistic statistic = statisticRepository.findById(statisticId).orElse(null);
        if (statistic == null) {
            throw new IllegalStateException("nie ma statystyki o id= " + statisticId);
        }
        statistic.setNumberOfTries(statisticRequest.getNumberOfTries());
        statistic.setUser(statisticRequest.getUser());
        statistic.setCorrectWord(statisticRequest.getCorrectWord());
        statisticRepository.update(statistic);
        return StatisticMapper.objectToResponseDto(statistic);
    }

    private int numberOfStatisticsOfOneWord(int wordId) {
        return statisticRepository.numberOfStatisticsOfOneWord(wordId);
    }

    private int numberOfUsersWithTriesNumberOfOneWord(int wordId, int numberOfTries) {
        return statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, numberOfTries);
    }

    public ArrayList<Double> perentageOfUsersWithNumberOfTries(int wordId) {
        ArrayList<Double> stats = new ArrayList<>();
        double users = (double) numberOfStatisticsOfOneWord(wordId);
        for (int i = Constants.MIN_NUMBER_OF_ATTEMPTS; i <= Constants.MAX_NUMBER_OF_ATTEMPTS; i++) {
            double percent = numberOfUsersWithTriesNumberOfOneWord(wordId, i) / users * 100;
            stats.add(percent);
        }
        return stats;
    }

    public ArrayList<Long> getWordsGuessedByUser(Long userId) {
        return statisticRepository.getWordsGuessedByUser(userId);
    }

    public StatisticResponseDto getStatisticBLoginAndWordName(Long wordId, Long userId) {
        Statistic statistic = statisticRepository.getStatisticByUserAndWord(userId, wordId);
        return StatisticMapper.objectToResponseDto(statistic);
    }
}

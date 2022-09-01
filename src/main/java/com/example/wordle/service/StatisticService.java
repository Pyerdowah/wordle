package com.example.wordle.service;

import com.example.wordle.Constants;
import com.example.wordle.dto.StatisticRequestedDto;
import com.example.wordle.dto.StatisticResponseDto;
import com.example.wordle.mapper.StatisticMapper;
import com.example.wordle.model.Statistic;
import com.example.wordle.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Statistic statistic= statisticRepository.save(statisticRequest);
        return StatisticMapper.objectToResponseDto(statistic);
    }

    public List<StatisticResponseDto> getAllStatistics() {
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

    public Map<Integer, Double> perentageOfUsersWithNumberOfTries(int wordId) {
        Map<Integer, Double> stats = new HashMap<>();
        double users = (double) numberOfStatisticsOfOneWord(wordId);
        for (int i = Constants.MIN_NUMBER_OF_ATTEMPTS; i <= Constants.MAX_NUMBER_OF_ATTEMPTS; i++) {
            double percent = numberOfUsersWithTriesNumberOfOneWord(wordId, i) / users * 100;
            stats.put(i, percent);
        }
        return stats;
    }
}

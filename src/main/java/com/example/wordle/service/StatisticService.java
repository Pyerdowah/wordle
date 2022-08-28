package com.example.wordle.service;

import com.example.wordle.model.Statistic;
import com.example.wordle.model.User;
import com.example.wordle.repository.StatisticRepository;
import com.example.wordle.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class StatisticService {
    private final StatisticRepository statisticRepository;
    @Autowired
    public StatisticService(StatisticRepository statisticRepository){
        this.statisticRepository = statisticRepository;
    }

    public Statistic addNewStatistic(Statistic statistic){
        statisticRepository.save(statistic);
        return statistic;
    }

    public List<Statistic> getAllStatistics(){
        return statisticRepository.findAll();
    }

    public void deleteStatistic(Statistic statistic) {
        statisticRepository.deleteById(statistic.getStatisticId());
    }

    public Statistic updateStatistic(Long statisticId, Statistic statisticRequest) {
        Optional<Statistic> statistic = statisticRepository.findById(statisticId);
        if (statistic.isEmpty()) {
            throw new IllegalStateException("nie ma takiej statystyki");
        }
        statistic.get().setNumberOfTries(statisticRequest.getNumberOfTries());
        statistic.get().setUser(statisticRequest.getUser());
        statistic.get().setCorrectWord(statisticRequest.getCorrectWord());
        statisticRepository.update(statistic.get());
        return statistic.get();
    }

    private Float numberOfStatisticsOfOneWord(int wordId){
        return statisticRepository.numberOfStatisticsOfOneWord(wordId).floatValue();
    }

    private Float numberOfUsersWithTriesNumberOfOneWord(int wordId, int numberOfTries){
        return statisticRepository.numberOfUsersWithTriesNumberOfOneWord(wordId, numberOfTries).floatValue();
    }

    public Map<Integer, String> perentageOfUsersWithNumberOfTries(int wordId){
        Map<Integer, String> stats = new HashMap<>();
        float users = numberOfStatisticsOfOneWord(wordId);
        int minNumberOfAttempts = 1;
        int maxNumberOfAttempts = 6;
        for (int i = minNumberOfAttempts; i <= maxNumberOfAttempts; i++){
            float percent = numberOfUsersWithTriesNumberOfOneWord(wordId, i) / users * 100;
            String stringPercent = String.valueOf(percent);
            stats.put(i, stringPercent + " %");
        }
        return stats;
    }
}

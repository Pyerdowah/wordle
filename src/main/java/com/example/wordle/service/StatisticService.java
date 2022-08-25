package com.example.wordle.service;

import com.example.wordle.model.Statistic;
import com.example.wordle.model.User;
import com.example.wordle.repository.StatisticRepository;
import com.example.wordle.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
}

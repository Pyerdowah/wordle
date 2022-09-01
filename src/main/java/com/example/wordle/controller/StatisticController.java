package com.example.wordle.controller;

import com.example.wordle.dto.StatisticRequestedDto;
import com.example.wordle.dto.StatisticResponseDto;
import com.example.wordle.mapper.StatisticMapper;
import com.example.wordle.model.Statistic;
import com.example.wordle.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @GetMapping(path = "/getAllStatistics")
    public List<StatisticResponseDto> getAllStatistics() {
        return statisticService.getAllStatistics();
    }

    @PostMapping("/addNewStatistic")
    public ResponseEntity<StatisticResponseDto> addNewStatistic(@RequestBody StatisticRequestedDto statisticRequestedDto) {
        return new ResponseEntity<>(statisticService.addNewStatistic(statisticRequestedDto), HttpStatus.CREATED);
    }

    @PutMapping("/updateStatistic/{id}")
    public ResponseEntity<StatisticResponseDto> updateStatistic(@PathVariable(name = "id") Long statisticId, @RequestBody StatisticRequestedDto statisticRequestedDto) {
        return ResponseEntity.ok()
                .body(statisticService.updateStatistic(statisticId, statisticRequestedDto));
    }

    @GetMapping("/wordStatistic/{wordId}")
    public ResponseEntity<Map<Integer, Double>> getStatistic(@PathVariable String wordId) {
        Map<Integer, Double> stats = statisticService.perentageOfUsersWithNumberOfTries(Integer.parseInt(wordId));
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @DeleteMapping("/deleteStatistic")
    public ResponseEntity<?> deleteStatistic(@RequestBody StatisticRequestedDto statisticRequestedDto) {
        statisticService.deleteStatistic(statisticRequestedDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

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
import java.util.stream.Collectors;
@RestController
public class StatisticController {
    private final StatisticService statisticService;

    @Autowired
    public StatisticController(StatisticService statisticService){
        this.statisticService = statisticService;
    }

    @GetMapping(path = "/getAllStatistics")
    public List<StatisticResponseDto> getAllStatistics(){
        return statisticService.getAllStatistics().stream().map(StatisticMapper::objectToResponseDto).collect(Collectors.toList());
    }

    @PostMapping("/addNewStatistic")
    public ResponseEntity<StatisticResponseDto> addNewStatistic(@RequestBody StatisticRequestedDto statisticRequestedDto) {
        // convert DTO to entity
        Statistic statisticRequest = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        Statistic statistic = statisticService.addNewStatistic(statisticRequest);
        // convert entity to DTO
        StatisticResponseDto statisticResponseDto = StatisticMapper.objectToResponseDto(statistic);
        return new ResponseEntity<>(statisticResponseDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateStatistic/{id}")
    public ResponseEntity<StatisticResponseDto> updateStatistic(@PathVariable(name = "id") Long statisticId, @RequestBody StatisticRequestedDto statisticRequestedDto) {
        // convert DTO to Entity
        Statistic statisticRequest = StatisticMapper.requestedDtoToObject(statisticRequestedDto);

        Statistic statistic = statisticService.updateStatistic(statisticId, statisticRequest);

        // entity to DTO
        StatisticResponseDto statisticResponseDto = StatisticMapper.objectToResponseDto(statistic);

        return ResponseEntity.ok().body(statisticResponseDto);
    }

    @DeleteMapping("/deleteStatistic")
    public void deleteStatistic(@RequestBody StatisticRequestedDto statisticRequestedDto) {
        Statistic statisticRequest = StatisticMapper.requestedDtoToObject(statisticRequestedDto);
        statisticService.deleteStatistic(statisticRequest);
    }
}

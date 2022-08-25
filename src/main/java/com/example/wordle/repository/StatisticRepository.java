package com.example.wordle.repository;

import com.example.wordle.model.Statistic;
import com.example.wordle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
@Repository
@Transactional
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    @Modifying
    @Query("update Statistic s set s.numberOfTries = :#{#statistic.numberOfTries}, s.user = :#{#statistic.user} where s.statisticId = :#{#statistic.statisticId}")
    void update(Statistic statistic);
}

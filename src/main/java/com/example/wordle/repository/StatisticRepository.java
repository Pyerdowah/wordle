package com.example.wordle.repository;

import com.example.wordle.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
    @Modifying
    @Query("update Statistic s set s.numberOfTries = :#{#statistic.numberOfTries}, s.user = :#{#statistic.user} where s.statisticId = :#{#statistic.statisticId}")
    void update(Statistic statistic);

    @Query(value = "select count(*) from statistics where word_id = :#{#wordId}", nativeQuery = true)
    Integer numberOfStatisticsOfOneWord(int wordId);

    @Query(value = "select count(*) from statistics where word_id = :#{#wordId} and number_of_tries = :#{#numberOfTries}", nativeQuery = true)
    Integer numberOfUsersWithTriesNumberOfOneWord(int wordId, int numberOfTries);
}

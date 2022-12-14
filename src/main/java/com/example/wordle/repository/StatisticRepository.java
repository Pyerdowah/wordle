package com.example.wordle.repository;

import com.example.wordle.model.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

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

    @Query(value = "select word_id from statistics where user_id = :#{#userId}", nativeQuery = true)
    ArrayList<Long> getWordsGuessedByUser(Long userId);

    @Query(value = "select * from statistics where user_id = :#{#userId} and word_id = :#{#wordId}", nativeQuery = true)
    Statistic getStatisticByUserAndWord(Long userId, Long wordId);

}

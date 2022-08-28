package com.example.wordle.repository;

import com.example.wordle.model.User;
import com.example.wordle.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("select w from Word w where w.wordName = ?1")
    Optional<Word> findWordByName(String wordName);

    @Modifying
    @Query("update Word w set w.wordName = :#{#word.wordName} where w.wordId = :#{#word.wordId}")
    void update(Word word);

    @Query(value = "select * from words order by random() limit 1", nativeQuery = true)
    Word getRandomWord();
}

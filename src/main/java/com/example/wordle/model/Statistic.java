package com.example.wordle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long statisticId;
    private int numberOfTries;
    @ManyToOne
    private User user;
    @OneToOne
    private Word correctWord;
    @ManyToMany(mappedBy = "statisticList")
    private List<Word> wordList;
}

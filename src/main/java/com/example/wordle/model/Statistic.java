package com.example.wordle.model;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long statisticId;
    private int numberOfTries;
    @ManyToOne
    User user;
}

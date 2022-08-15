package com.example.wordle.model;

import javax.persistence.*;

@Entity
@Table(name = "statsAndWords")
public class StatsAndWords {
    @EmbeddedId
    private StatsAndWordsId statsAndWordsId;

    @ManyToOne
    @MapsId("statisticId")
    private Statistic statistic;

    @ManyToOne
    @MapsId("wordId")
    private Word word;

    private boolean correctWord;

}

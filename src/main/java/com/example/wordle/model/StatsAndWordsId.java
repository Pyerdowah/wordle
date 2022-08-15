package com.example.wordle.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class StatsAndWordsId implements Serializable {
    long statisticId;
    long wordId;
}

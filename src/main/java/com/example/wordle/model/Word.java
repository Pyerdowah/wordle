package com.example.wordle.model;

import javax.persistence.*;

@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long wordId;
    private String wordName;
}

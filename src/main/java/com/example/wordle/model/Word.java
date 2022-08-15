package com.example.wordle.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long wordId;
    private String wordName;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "statsAndWords",
            joinColumns = { @JoinColumn(name = "wordID") },
            inverseJoinColumns = { @JoinColumn(name = "statisticId") }
    )
    private List<Statistic> statisticList;
}

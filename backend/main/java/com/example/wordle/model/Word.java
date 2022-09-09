package com.example.wordle.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "words")
public class Word {
    @Id
    @SequenceGenerator(name = "word_sequence", sequenceName = "word_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "word_sequence")
    @Column(name = "word_id")
    private Long wordId;
    @Column(name = "word_name")
    private String wordName;
}

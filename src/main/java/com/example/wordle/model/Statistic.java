package com.example.wordle.model;

import lombok.*;
import org.springframework.data.jpa.repository.Modifying;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statistics")
public class Statistic {
    @Id
    @SequenceGenerator(name = "statistic_sequence", sequenceName = "statistic_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statistic_sequence")
    @Column(name = "statistic_id")
    private Long statisticId;
    @Column(name = "number_of_tries")
    private int numberOfTries;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "word_id")
    private Word correctWord;
}

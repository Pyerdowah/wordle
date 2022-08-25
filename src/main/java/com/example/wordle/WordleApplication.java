package com.example.wordle;

import com.example.wordle.mapper.UserMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WordleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WordleApplication.class, args);
    }

}

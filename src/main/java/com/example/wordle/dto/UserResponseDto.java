package com.example.wordle.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserResponseDto {
    private String login;
    private String password;
    private LoginStatus status;
}

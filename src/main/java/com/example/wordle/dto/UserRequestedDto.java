package com.example.wordle.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRequestedDto {
    private Long userId;
    private String login;
    private String password;
    private LoginStatus status;
}

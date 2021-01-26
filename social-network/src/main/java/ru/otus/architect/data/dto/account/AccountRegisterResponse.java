package ru.otus.architect.data.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountRegisterResponse {
    private Long id;
    private String login;
    private String name;
    private String status;

    private String jwtToken;
}

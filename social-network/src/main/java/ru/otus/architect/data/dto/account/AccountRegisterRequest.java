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
public class AccountRegisterRequest {
    private String login;
    private String password;

    private String name;
    private String surname;
    private String gender;
    private String city;
    private Integer age;
}

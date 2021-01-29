package ru.otus.architect.generator.data;

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
public class Auth {
    private Long userId;
    private String login;
    private String password;
    private String role;

    public Auth(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Auth(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

}

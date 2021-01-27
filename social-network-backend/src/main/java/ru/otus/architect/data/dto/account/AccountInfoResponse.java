package ru.otus.architect.data.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.otus.architect.data.model.AccountFriend;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountInfoResponse {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String gender;
    private String city;
    private Integer age;

    private AccountFriend accountFriendStatus;
}

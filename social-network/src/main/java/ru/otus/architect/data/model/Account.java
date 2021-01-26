package ru.otus.architect.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String gender;
    private String city;
    private Integer age;
    private List<AccountFriend> friendIds = new ArrayList<>();
}

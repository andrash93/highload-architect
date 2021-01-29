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
public class Account {
    private Long id;
    private String login;
    private String name;
    private String surname;
    private String gender;
    private String city;
    private Integer age;
}

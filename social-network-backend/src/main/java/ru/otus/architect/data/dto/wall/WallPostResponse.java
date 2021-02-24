package ru.otus.architect.data.dto.wall;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WallPostResponse {
    private Long id;
    private Long userId;
    private String fullUserName;
    private Timestamp dateCreated;
    private String text;
}

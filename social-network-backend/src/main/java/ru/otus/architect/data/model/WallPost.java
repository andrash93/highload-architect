package ru.otus.architect.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class WallPost implements Serializable {
   private Long id;
   private Long userId;
   private String fullUserName;
   private Timestamp dateCreated;
   private String text;
}

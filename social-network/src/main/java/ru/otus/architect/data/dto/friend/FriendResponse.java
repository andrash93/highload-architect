package ru.otus.architect.data.dto.friend;

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
public class FriendResponse {
    private AccountFriend accountFriend;
}

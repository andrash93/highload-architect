package ru.otus.architect.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.otus.architect.data.FriendStatus;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AccountFriend {
    private Long senderPersonId;
    private Long receiverPersonId;
    private Timestamp creationDate;
    private Timestamp confirmationDate;
    private FriendStatus status;

    public AccountFriend(FriendStatus status) {
        this.status = status;
    }
}

package com.nikita.bulak.mediaplatform.friendship.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendshipRequest {
    //todo
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private long requestingUserId;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "friend_id", referencedColumnName = "id")
    private long friendId;
    private boolean confirmed;
}

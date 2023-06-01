package com.nikita.bulak.mediaplatform.message;

import com.nikita.bulak.mediaplatform.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> getMessagesByAuthorIdAndRecipientIdOrAuthorIdAndRecipientId(Long authorId1,
                                                                              Long recipientId1,
                                                                              Long authorId2,
                                                                              Long recipientId2);
}

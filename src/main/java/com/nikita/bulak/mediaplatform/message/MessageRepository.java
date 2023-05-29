package com.nikita.bulak.mediaplatform.message;

import com.nikita.bulak.mediaplatform.message.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}

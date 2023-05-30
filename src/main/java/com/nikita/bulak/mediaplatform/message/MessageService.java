package com.nikita.bulak.mediaplatform.message;

import com.nikita.bulak.mediaplatform.message.dto.MessageDto;

import java.util.List;

public interface MessageService {
    Boolean createMessage(MessageDto messageDto);

    List<MessageDto> getMessages(Long friendId);
}

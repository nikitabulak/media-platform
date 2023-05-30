package com.nikita.bulak.mediaplatform.message;

import com.nikita.bulak.mediaplatform.message.dto.MessageDto;
import com.nikita.bulak.mediaplatform.message.model.Message;
import com.nikita.bulak.mediaplatform.user.model.User;

public class MessageMapper {
    public static MessageDto toMessageDto(Message message) {
        return new MessageDto(message.getId(),
                message.getAuthor().getId(),
                message.getRecipient().getId(),
                message.getCreationDate(),
                message.getText());
    }

    public static Message toMessage(MessageDto messageDto, User author, User recipient) {
        return new Message(0L,
                author,
                recipient,
                messageDto.getCreationDate(),
                messageDto.getText());
    }
}

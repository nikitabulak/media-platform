package com.nikita.bulak.mediaplatform.message;

import com.nikita.bulak.mediaplatform.exception.FriendNotFoundException;
import com.nikita.bulak.mediaplatform.exception.IllegalOperationException;
import com.nikita.bulak.mediaplatform.exception.UserNotFoundException;
import com.nikita.bulak.mediaplatform.message.dto.MessageDto;
import com.nikita.bulak.mediaplatform.message.model.Message;
import com.nikita.bulak.mediaplatform.user.AuthUtils;
import com.nikita.bulak.mediaplatform.user.model.User;
import com.nikita.bulak.mediaplatform.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public MessageDto createMessage(MessageDto messageDto) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User friend = userRepository.findById(messageDto.getRecipientId())
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + messageDto.getRecipientId()));
        if (!user.getId().equals(messageDto.getAuthorId())) {
            throw new IllegalOperationException("Author's id doesn't match with authorized user's id. " +
                    "You can only send messages for authorized user.");
        }
        if (user.getFriends().contains(friend)) {
            Message message = messageRepository.save(MessageMapper.toMessage(messageDto, user, friend));
            return MessageMapper.toMessageDto(message);
        } else {
            throw new FriendNotFoundException(String.format("User with userId = %s doesn't have friend with userId = %s",
                    user.getId(), friend.getId()));
        }
    }

    @Override
    public List<MessageDto> getMessages(Long friendId) {
        User user = userRepository.findById(AuthUtils.getCurrentUserId()).get();
        User friend = userRepository.findById(friendId)
                .orElseThrow(() -> new UserNotFoundException("There is no user with id = " + friendId));
        if (user.getFriends().contains(friend)) {
            return messageRepository.getMessagesByAuthorIdAndRecipientIdOrAuthorIdAndRecipientId(user.getId(), friendId, friendId, user.getId())
                    .stream()
                    .sorted(Comparator.comparing(Message::getCreationDate).reversed())
                    .map(MessageMapper::toMessageDto)
                    .collect(Collectors.toList());
        } else {
            throw new FriendNotFoundException(String.format("User with userId = %s doesn't have friend with userId = %s",
                    user.getId(), friend.getId()));
        }
    }
}

package com.nikita.bulak.mediaplatform.message;

import com.nikita.bulak.mediaplatform.message.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/friends/")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/message")
    public Boolean sendMessage(@RequestBody MessageDto messageDto) {
        return messageService.createMessage(messageDto);
    }

    @GetMapping("/{friendId}/messages")
    public List<MessageDto> getMessages(@PathVariable Long friendId) {
        return messageService.getMessages(friendId);
    }
}

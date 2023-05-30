package com.nikita.bulak.mediaplatform.message.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class MessageDto {
    private Long id;
    private Long authorId;
    private Long recipientId;
    private LocalDateTime creationDate;
    private String text;
}

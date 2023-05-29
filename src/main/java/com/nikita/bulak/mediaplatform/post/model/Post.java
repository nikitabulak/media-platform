package com.nikita.bulak.mediaplatform.post.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nikita.bulak.mediaplatform.user.model.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
@ToString
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private User author;
    private String header;
    private String text;
    //todo
    private String imageName;
    @Column(name = "creation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
}

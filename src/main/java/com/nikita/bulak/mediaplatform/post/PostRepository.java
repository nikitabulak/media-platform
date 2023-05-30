package com.nikita.bulak.mediaplatform.post;

import com.nikita.bulak.mediaplatform.post.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthorId(Long authorId, Pageable pageable);
    List<Post> findAllByAuthorIdIn(List<Long> authorIds, Pageable pageable);
}

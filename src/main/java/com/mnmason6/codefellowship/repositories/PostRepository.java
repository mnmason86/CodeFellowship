package com.mnmason6.codefellowship.repositories;

import com.mnmason6.codefellowship.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}

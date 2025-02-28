package com.newgen.API.BlogApp.repository;

import com.newgen.API.BlogApp.entity.Comment;
import com.newgen.API.BlogApp.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPostId(long postId);
}

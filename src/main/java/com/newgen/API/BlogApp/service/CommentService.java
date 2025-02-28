package com.newgen.API.BlogApp.service;

import com.newgen.API.BlogApp.entity.Comment;
import com.newgen.API.BlogApp.payload.CommentDto;
import com.newgen.API.BlogApp.repository.CommentRepository;
import com.newgen.API.BlogApp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    CommentDto CreateComment(long postID,CommentDto commentDto);
    List<CommentDto> GetCommentsPostByid(long postId);
    CommentDto getCommentById(Long postId, Long commentId);
    CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest);
    void deleteComment(Long postId, Long commentId);
}

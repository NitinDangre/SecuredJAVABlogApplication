package com.newgen.API.BlogApp.service.impl;
import aj.org.objectweb.asm.commons.Remapper;
import com.newgen.API.BlogApp.entity.Comment;
import com.newgen.API.BlogApp.entity.Post;
import com.newgen.API.BlogApp.exception.BlogAPIException;
import com.newgen.API.BlogApp.exception.ResourceNotFoundException;
import com.newgen.API.BlogApp.payload.CommentDto;
import com.newgen.API.BlogApp.repository.CommentRepository;
import com.newgen.API.BlogApp.repository.PostRepository;
import com.newgen.API.BlogApp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CommentServiceImpl implements CommentService {
    private ModelMapper mapper;
    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo,ModelMapper mapper) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
        this.mapper=mapper;
    }

    @Override
    public CommentDto CreateComment(long postID, CommentDto commentDto) {
        Post post = postRepo.findById(postID).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postID)
        );
        Comment comment = mapToEntity(commentDto);
        comment.setPost(post);
        Comment newComent = commentRepo.save(comment);
        return mapToDTO(newComent);
    }

    @Override
    public List<CommentDto> GetCommentsPostByid(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment -> mapToDTO(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }

        return mapToDTO(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, long commentId, CommentDto commentRequest) {

        // retrieve post entity by id

        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));
        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));
        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");

        }
        comment.setName(commentRequest.getName());
        comment.setEmail(commentRequest.getEmail());
        comment.setBody(commentRequest.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDTO(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        // retrieve post entity by id
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", postId));

        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(() ->
                new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Comment does not belongs to post");
        }
        commentRepo.delete(comment);
    }

    private CommentDto mapToDTO(Comment comment){
        CommentDto commentDtos = mapper.map(comment, CommentDto.class);
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        commentDto.setBody(comment.getBody());
        return  commentDtos;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment commentS = mapper.map(commentDto, Comment.class);
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
//        comment.setBody(commentDto.getBody());
        return  commentS;
    }
}

package com.newgen.API.BlogApp.Controller;

import com.newgen.API.BlogApp.entity.Comment;
import com.newgen.API.BlogApp.payload.CommentDto;
import com.newgen.API.BlogApp.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("posts/{postId}/comments")
    public ResponseEntity<Object> createComment(@PathVariable(value = "postId") long postId,
                                                    @Valid@RequestBody CommentDto commentDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto Dto = commentService.CreateComment(postId, commentDto);
        return new ResponseEntity(Dto, HttpStatus.CREATED);
    }
    @GetMapping("posts/{postId}/comments")
    public List<CommentDto> GetCommentByPostId(@PathVariable("postId") long postId){
        return commentService.GetCommentsPostByid(postId);

    }

    @PutMapping("/posts/{postId}/comments/{id}")
    public ResponseEntity<Object> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @Valid @RequestBody CommentDto commentDto,
                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CommentDto updatedComment = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}/comments/{id}")

    public ResponseEntity<String> deleteComment(@PathVariable(value = "postId") Long postId,
                                                @PathVariable(value = "id") Long commentId){

        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);

    }
}

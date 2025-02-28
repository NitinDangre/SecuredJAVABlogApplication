package com.newgen.API.BlogApp.Controller;
import com.newgen.API.BlogApp.payload.PostDto;
import com.newgen.API.BlogApp.payload.PostResponse;
import com.newgen.API.BlogApp.service.PostService;
import com.newgen.API.BlogApp.service.impl.PostServiceImpl;
import com.newgen.API.BlogApp.utils.AppConstant;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @PostMapping
    public ResponseEntity<Object> createPost(@Valid@RequestBody PostDto postDto, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
          return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.INTERNAL_SERVER_ERROR);
       }
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }
    //localhost:8080/api/posts
    @GetMapping
    public PostResponse getAllPost(
           @RequestParam(value ="pageNo",defaultValue = AppConstant.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
           @RequestParam(value = "pageSize", defaultValue = AppConstant.DEFAULT_PAGE_SIZE, required = false) int pageSize,
           @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
           @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIR, required = false) String sortDir
    ){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
    }
   // localhost:8080/api/posts/?id=1----@QueryParameter("id")
   //localhost:8080/api/posts/1------@PathVariable("id")
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return ResponseEntity.ok(postService.getPostByID(id));
    }
    //localhost:8080/api/posts/1------@PathVariable("id")
    @PutMapping("/{id}")
   public ResponseEntity<Object> UpdatedPost(@Valid @RequestBody PostDto postDto, @PathVariable("id") long id,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.UpdatePost(postDto, id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    //localhost:8080/api/posts/1------@PathVariable("id")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> DeletePostById(@PathVariable("id") long id){
        postService.DeletePostById(id);
        return new ResponseEntity<>("Post entity deleted successfully.", HttpStatus.OK);
    }
}

package com.newgen.API.BlogApp.service.impl;

import com.newgen.API.BlogApp.entity.Comment;
import com.newgen.API.BlogApp.entity.Post;
import com.newgen.API.BlogApp.exception.ResourceNotFoundException;
import com.newgen.API.BlogApp.payload.CommentDto;
import com.newgen.API.BlogApp.payload.PostDto;
import com.newgen.API.BlogApp.payload.PostResponse;
import com.newgen.API.BlogApp.repository.PostRepository;
import com.newgen.API.BlogApp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class PostServiceImpl implements PostService {
    private ModelMapper mapper;
 private PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo,ModelMapper mapper) {
        this.postRepo = postRepo;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newPost = postRepo.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDTO(newPost);
        return postResponse;
    }
    //:::::::::::::::::Stream API Feature::::::::::::::::::::::::::::::::::::::::::
    //Get All Posts From DATA Base
    @Override
    public PostResponse getAllPost(int pageNo,int pageSize,String sortBy,String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable= PageRequest.of(pageNo,pageSize, sort);
        //Pageable pageable= PageRequest.of(pageNo,pageSize);
        Page<Post> posts=postRepo.findAll(pageable);
        List<Post> content = posts.getContent();
        List<PostDto> contents = content.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(contents);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setTotalElement(posts.getTotalElements());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }
    //Get Posts Based On id From DATA Base
    @Override
    public PostDto getPostByID(long id)  {
        Post post = postRepo.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Post", "id", id));
        PostDto postDto = mapToDTO(post);
        return postDto;
    }
    //Update Posts Based On id into DATA Base
    @Override
    public PostDto UpdatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));
        //post.setId(postDto.getId());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        post.setTitle(postDto.getTitle());
        Post newPost = postRepo.save(post);
         return mapToDTO(newPost);
    }

    @Override
    public void DeletePostById(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id));
        postRepo.deleteById(id);
    }

    // convert Entity into DTO
        private PostDto mapToDTO(Post post){
            PostDto postDto = mapper.map(post, PostDto.class);
//            PostDto postDto = new PostDto();
//            postDto.setId(post.getId());
//            postDto.setTitle(post.getTitle());
//            postDto.setDescription(post.getDescription());
//            postDto.setContent(post.getContent());
            return postDto;
        }
        // convert DTO to entity
        private Post mapToEntity(PostDto postDto){
            Post post  = mapper.map(postDto, Post.class);
//            Post post = new Post();
//            post.setId(postDto.getId());
//            post.setTitle(postDto.getTitle());
//            post.setDescription(postDto.getDescription());
//            post.setContent(postDto.getContent());
            return post;
        }

}

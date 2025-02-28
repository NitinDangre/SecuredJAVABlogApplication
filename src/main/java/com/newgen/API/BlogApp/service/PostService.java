package com.newgen.API.BlogApp.service;

import com.newgen.API.BlogApp.payload.PostDto;
import com.newgen.API.BlogApp.payload.PostResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {

     PostDto createPost(PostDto postDto) ;
     PostResponse getAllPost(int pageNo, int PageSize,String sortBy,String sortDir);
      PostDto getPostByID(long id) ;

    PostDto UpdatePost(PostDto postDto, long id);

    void DeletePostById(long id);
}

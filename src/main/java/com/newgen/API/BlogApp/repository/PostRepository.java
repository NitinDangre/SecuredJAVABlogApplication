package com.newgen.API.BlogApp.repository;

import com.newgen.API.BlogApp.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post , Long> {

}

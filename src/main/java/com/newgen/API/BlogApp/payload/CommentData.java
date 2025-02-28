package com.newgen.API.BlogApp.payload;


import com.newgen.API.BlogApp.entity.Comment;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommentData {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="TITLE", nullable=false)
    private String title;
    @Column(name="DESCRIPTION", nullable=false)
    private String description;
    @Column(name="CONTENT", nullable=false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

}

package com.newgen.API.BlogApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name="BLOGAPP",
        uniqueConstraints ={@UniqueConstraint(columnNames ={"TITLE"})}
      )
public class Post {
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

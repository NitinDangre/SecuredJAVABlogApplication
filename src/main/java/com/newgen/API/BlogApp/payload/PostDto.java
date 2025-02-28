package com.newgen.API.BlogApp.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto {
    private Long id;
    @NotNull
    @Size(min=2,message="Post Title Should Have At-least 2 Character's")
    private String title;
    @NotNull
    @Size(min=10,message="Post Description Should Have At-least 10 Character's")
    private String description;
    @NotEmpty(message="Post Content Should Not Empty")
    private String content;
}

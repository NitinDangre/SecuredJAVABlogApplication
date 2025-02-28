package com.newgen.API.BlogApp.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;
    @NotNull
    @Size(min=2,message="Comment Name Should Have At-least 2 Character's")
    private String name;
    @Email(message = "Email Format not Validate")
    @Size(min = 10,max=100,message = "Email Size Should have At-least 10 characters")
    private String email;
    @NotNull
    @Size(min=2,message="Comment Body Should Have At-least 2 Character's")
    private String body;
}
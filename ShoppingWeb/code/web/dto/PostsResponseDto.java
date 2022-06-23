package com.book.springboot.web.dto;

import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.posts.State;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private Integer price;
    private State state;

    public PostsResponseDto(Posts entity){
        this.id=entity.getId();
        this.title=entity.getTitle();
        this.content=entity.getContent();
        this.author=entity.getAuthor();
        this.price=entity.getPrice();
        this.state=entity.getState();
    }
}

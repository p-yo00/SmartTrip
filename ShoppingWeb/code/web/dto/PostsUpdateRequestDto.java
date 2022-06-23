package com.book.springboot.web.dto;

import com.book.springboot.domain.posts.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {
    private String title;
    private String content;
    private Integer price;

    @Builder
    public PostsUpdateRequestDto(String title, String content, Integer price){
        this.title=title;
        this.content=content;
        this.price=price;
    }
}

package com.book.springboot.web.dto;

import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.posts.State;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.criteria.CriteriaBuilder;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private Integer price;
    private String content;
    private String author;
    private State state;
    private boolean buyer;
    private boolean seller;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author, Integer price){
        this.title=title;
        this.content=content;
        this.author=author;
        this.price=price;
        this.state=State.SALE;
        this.buyer=false;
        this.seller=false;
    }

    public Posts toEntity(){
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .price(price)
                .state(State.SALE)
                .buyer(false)
                .seller(false)
                .build();
    }
}

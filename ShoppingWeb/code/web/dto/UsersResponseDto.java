package com.book.springboot.web.dto;

import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.user.User;
import lombok.Getter;

@Getter
public class UsersResponseDto {
    private Long id;
    private String email;
    private String name;
    private String picture;
    private Integer point;

    public UsersResponseDto(User entity){
        this.id=entity.getId();
        this.email=entity.getEmail();
        this.name=entity.getName();
        this.picture=entity.getPicture();
        this.point=entity.getPoint();
    }

    public UsersResponseDto(UsersResponseDto entity){
        this.id=entity.getId();
        this.email=entity.getEmail();
        this.name=entity.getName();
        this.picture=entity.getPicture();
        this.point=entity.getPoint();
    }
}

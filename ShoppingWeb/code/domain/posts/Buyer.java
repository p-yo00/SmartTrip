package com.book.springboot.domain.posts;

import com.book.springboot.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Buyer {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Long postId;

    private Long userId;

    @Builder
    public Buyer(Long postId, Long userId){
        this.postId=postId;
        this.userId=userId;
    }
}

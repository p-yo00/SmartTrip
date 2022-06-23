package com.book.springboot.web.dto;

import com.book.springboot.domain.posts.Buyer;
import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuyerSaveRequestDto {
    private Long postId;
    private Long userId;

    @Builder
    public BuyerSaveRequestDto(Long postId, Long userId){
        this.postId=postId;
        this.userId=userId;
    }

    public BuyerSaveRequestDto(Buyer buyer) {
        postId=buyer.getPostId();
        userId=buyer.getUserId();
    }

    public Buyer toEntity(){
        return Buyer.builder()
                .postId(postId)
                .userId(userId)
                .build();
    }
}

package com.book.springboot.domain.posts;

import com.book.springboot.domain.BaseTimeEntity;
import com.book.springboot.domain.user.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length=500, nullable=false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable=false)
    private String content;

    private String author;

    private Integer price;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private State state;

    private boolean buyer;
    private boolean seller;

    @Builder
    public Posts(String title, String content, String author, Integer price, State state, boolean buyer, boolean seller){
        this.title=title;
        this.content=content;
        this.author=author;
        this.price=price;
        this.state=state;
        this.buyer=buyer;
        this.seller=seller;
    }

    public void update(String title, String content, Integer price){
        this.title=title;
        this.content=content;
        this.price=price;
    }

    public void stateUpdate(State state){
        this.state=state;
    }

    public void setBuyer(boolean state){
        this.buyer=state;
    }

    public void setSeller(boolean state){
        this.seller=state;
    }
}

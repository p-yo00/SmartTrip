package com.book.springboot.domain.user;

import com.book.springboot.config.auth.CustomOAuth2UserService;
import com.book.springboot.config.auth.dto.SessionUser;
import com.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.servlet.http.HttpSession;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String email;

    @Column
    private String picture;

    @Column
    private Integer point=0;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role, Integer point){
        this.name=name;
        this.email=email;
        this.picture=picture;
        this.role=role;
        this.point=point;
    }

    public void update(Integer point){
        this.point=point;

    }

    public User update2(String picture, String name){
        this.picture=picture;
        this.name=name;

        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}

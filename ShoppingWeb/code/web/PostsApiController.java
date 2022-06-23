package com.book.springboot.web;

import com.book.springboot.domain.posts.State;
import com.book.springboot.service.posts.PostsService;
import com.book.springboot.service.users.UsersService;
import com.book.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    private final PostsService postsService;
    private final UsersService usersService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto){
        return postsService.save(requestDto);
    }

    @PostMapping("/api/buy/{id}/{balance}")
    public Long buy(@PathVariable Long id, @PathVariable Integer balance, @RequestBody BuyerSaveRequestDto requestDto){
        usersService.update(id,balance);
        return postsService.save2(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @PutMapping("/api/point/{id}")
    public Long update(@PathVariable Long id, @RequestBody Integer point){
        return usersService.update(id, point);
    }

    @PutMapping("/api/deal/{postid}/{userid}")
    public Long deal(@PathVariable Long postid, @PathVariable Long userid, @RequestBody Integer point){
        postsService.stateUpdate(postid, State.ONGOING);
        return usersService.update(userid, point);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }

    @GetMapping("api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id){
        return postsService.findById(id);
    }
}

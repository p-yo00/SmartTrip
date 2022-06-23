package com.book.springboot.web;

import com.book.springboot.config.auth.dto.SessionUser;
import com.book.springboot.domain.posts.State;
import com.book.springboot.service.posts.PostsService;
import com.book.springboot.service.users.UsersService;
import com.book.springboot.web.dto.PostsResponseDto;
import com.book.springboot.web.dto.UsersResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    private final UsersService usersService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postsService.findAllDesc());
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user!=null){
            model.addAttribute("userId", user.getId());
            model.addAttribute("userName", user.getEmail());
            model.addAttribute("point", user.getPoint());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model){
        SessionUser users = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("author",users.getName());
        model.addAttribute("point",users.getPoint());
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}/{id2}")
    public String postsUpdate(@PathVariable Long id, @PathVariable Long id2, Model model){
        PostsResponseDto dto=postsService.findById(id);
        SessionUser users = (SessionUser) httpSession.getAttribute("user");
        UsersResponseDto dto2=usersService.findById(id2);
        int count=postsService.CountById(id);
        model.addAttribute("post",dto);
        model.addAttribute("user",dto2);
        model.addAttribute("buyerUser",postsService.findAllId(id));
        model.addAttribute("state",dto.getState().getTitle());
        model.addAttribute("count", count);
        if(dto.getState().equals(State.ONGOING)) model.addAttribute("isONGOING", true);
        if(users.getName().equals(dto.getAuthor())) return "posts-update";
        else return "posts-check";
    }

    @GetMapping("/deposit/{email}")
    public String deposit(@PathVariable String email, Model model){
        SessionUser users = (SessionUser) httpSession.getAttribute("user");
        model.addAttribute("point",users.getPoint());
        model.addAttribute("email",email);
        model.addAttribute("id",users.getId());

        return "point";
    }

}

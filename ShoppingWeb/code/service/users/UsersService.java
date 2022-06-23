package com.book.springboot.service.users;

import com.book.springboot.config.auth.dto.SessionUser;
import com.book.springboot.domain.posts.Buyer;
import com.book.springboot.domain.posts.Posts;
import com.book.springboot.domain.user.User;
import com.book.springboot.domain.user.UserRepository;
import com.book.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional
    public Long update(Long id, Integer point){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(
                        "회원이 없습니다. id="+id));
        user.update(point);
        userRepository.save(user);

        httpSession.setAttribute("user", new SessionUser(user));

        return id;
    }

    @Transactional
    public UsersResponseDto findById(Long id){
        User entity=userRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 사용자가 없습니다. id="+id));

        return new UsersResponseDto(entity);
    }


}

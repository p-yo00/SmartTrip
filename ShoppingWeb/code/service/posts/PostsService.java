package com.book.springboot.service.posts;

import com.book.springboot.config.auth.dto.SessionUser;
import com.book.springboot.domain.posts.*;
import com.book.springboot.domain.user.User;
import com.book.springboot.domain.user.UserRepository;
import com.book.springboot.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    private final PostsRepository postsRepository;
    private final BuyerRepository buyerRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto){
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long save2(BuyerSaveRequestDto requestDto){
        return buyerRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto){
        Posts posts = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(
                        "해당 게시글이 없습니다. id="+id));
        posts.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getPrice());

        return id;
    }

    @Transactional
    public Long stateUpdate(Long id, State state){
        Posts post = postsRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(
                        "없습니다. id="+id));
        post.stateUpdate(state);
        postsRepository.save(post);

        return id;
    }

    @Transactional
    public void delete(Long id){
        Posts posts=postsRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        postsRepository.delete(posts);
    }

    @Transactional
    public PostsResponseDto findById(Long id){
        Posts entity=postsRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("해당 게시글이 없습니다. id="+id));

        return new PostsResponseDto(entity);
    }

    @Transactional(readOnly=true)
    public List<PostsListResponseDto> findAllDesc(){
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly=true)
    public List<UsersResponseDto> findAllId(Long id){
        List<Buyer> list = buyerRepository.findAllId(id);
        List<UsersResponseDto> ulist = new ArrayList<UsersResponseDto>();
        for(int i=0; i<list.size(); i++){
            User user = userRepository.findById(list.get(i).getUserId()).orElseThrow(()->
                    new IllegalArgumentException("해당 사용자가 없음"));;
            UsersResponseDto userDto = new UsersResponseDto(user);
            ulist.add(userDto);
        }

        return ulist.stream()
                .map(UsersResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public int CountById(Long id){
        return buyerRepository.CountId(id);
    }
}

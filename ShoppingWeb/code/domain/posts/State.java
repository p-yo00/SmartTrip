package com.book.springboot.domain.posts;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {

    SALE("판매중"),
    ONGOING("거래중"),
    SOLDOUT("판매완료");

    private final String title;

}

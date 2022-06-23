package com.book.springboot.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.cache.spi.support.NaturalIdNonStrictReadWriteAccess;

@Getter
@NoArgsConstructor
public class UsersUpdateRequestDto {
    private Integer point;

    @Builder
    public UsersUpdateRequestDto(Integer point){
        this.point=point;
    }
}

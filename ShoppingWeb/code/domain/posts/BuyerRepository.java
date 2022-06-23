package com.book.springboot.domain.posts;

import com.book.springboot.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query("SELECT p FROM Buyer p WHERE p.postId=:idp ORDER BY p.userId DESC")
    List<Buyer> findAllId(@Param("idp") Long id);

    @Query("SELECT COUNT(*) FROM Buyer p WHERE p.postId=id")
    int CountId(@Param("id") Long countId);
}

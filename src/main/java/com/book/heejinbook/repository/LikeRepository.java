package com.book.heejinbook.repository;

import com.book.heejinbook.entity.Like;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    void deleteByUserAndReview(User user, Review review);
    boolean existsByUserAndReview(User user, Review review);
}

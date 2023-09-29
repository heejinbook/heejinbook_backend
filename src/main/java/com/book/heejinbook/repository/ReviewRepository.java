package com.book.heejinbook.repository;

import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Transactional
    @Modifying
    @Query("update Review r set r.isDeleted = true where r.id = ?1")
    void updateIsDeletedById(Long id);

    @Query("SELECT r FROM Review r JOIN FETCH Book b on b=:book WHERE r.isDeleted = false")
    Page<Review> findRandomReviews(@Param("book") Book book, Pageable pageable);

    @Query("SELECT new com.book.heejinbook.dto.review.response.ReviewListResponse " +
            "(r.id ,r.user.nickname, r.title, r.contents, r.createdAt) " +
            "FROM Review r " +
            "JOIN Book b " +
            "ON b = :book " +
            "WHERE r.isDeleted = false ")

    Page<ReviewListResponse> findByReviewList(Book book, Pageable pageable);
}

package com.book.heejinbook.repository.review;

import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewCustomRepository {
    boolean existsByBookAndUserAndIsDeletedFalse(Book book, User user);
    List<Review> findAllByUserAndIsDeletedFalseOrderByIdDesc(User user);
    @Query("select count(r) from Review r where r.book = ?1 and r.isDeleted = false")
    long countByBook(Book book);
    @Transactional
    @Modifying
    @Query("update Review r set r.isDeleted = true where r.id = ?1")
    void updateIsDeletedById(Long id);

    @Query("SELECT r FROM Review r JOIN FETCH Book b on b=:book WHERE r.isDeleted = false ORDER BY function('rand') ")
    Page<Review> findRandomReviews(@Param("book") Book book, Pageable pageable);

}

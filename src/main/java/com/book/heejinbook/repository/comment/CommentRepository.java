package com.book.heejinbook.repository.comment;

import com.book.heejinbook.entity.Comment;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByReviewAndIsDeletedFalse(Review review);
    List<Comment> findAllByUserAndIsDeletedFalseOrderByIdDesc(User user);
    @Transactional
    @Modifying
    @Query("update Comment c set c.isDeleted = true where c.id = ?1")
    int updateIsDeletedById(Long id);

    List<Comment> findAllByReviewAndIsDeletedFalseOrderByIdDesc(Review review);

}

package com.book.heejinbook.repository.review;

import com.book.heejinbook.dto.review.response.DetailReviewResponse;
import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.dto.review.response.ReviewSwiperResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewCustomRepository {

    Page<ReviewListResponse> findReviewList(Book book, Pageable pageable, String sort);
    List<ReviewSwiperResponse> findBestReviewList(Book book, User user, Integer size);

    DetailReviewResponse findDetailReviewByReviewId(Review review, User user);
}

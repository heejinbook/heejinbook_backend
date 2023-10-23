package com.book.heejinbook.repository.review;

import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {

    Page<ReviewListResponse> findReviewList(Book book, Pageable pageable, String sort);

}

package com.book.heejinbook.service;

import com.book.heejinbook.dto.review.request.RegisterReviewRequest;
import com.book.heejinbook.dto.review.response.DetailReviewResponse;
import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.dto.review.response.ReviewSwiperResponse;
import com.book.heejinbook.dto.vo.PaginationResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;
import com.book.heejinbook.error.domain.ReviewErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.BookRepository;
import com.book.heejinbook.repository.ReviewRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.security.AuthHolder;
import com.book.heejinbook.utils.PaginationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public void registerReview(Long bookId, RegisterReviewRequest registerReviewRequest) {

        User user = validUser(AuthHolder.getUserId());
        Book book = validBook(bookId);

        reviewRepository.save(Review.from(registerReviewRequest, book, user));
    }

    public List<ReviewSwiperResponse> getSwiperList(Long bookId, Integer size) {
        Book book = validBook(bookId);
        Pageable pageable = PageRequest.of(0, size);
        Page<Review> randomReviews = reviewRepository.findRandomReviews(book, pageable);
        return randomReviews.getContent().stream().map(ReviewSwiperResponse::from).collect(Collectors.toList());
    }

    public PaginationResponse<ReviewListResponse> getList(Long bookId, Pageable pageable) {
        Book book = validBook(bookId);
        Page<ReviewListResponse> pageData = reviewRepository.findByReviewList(book, pageable);

        return new PaginationBuilder<ReviewListResponse>()
                .hasNext(pageData.hasNext())
                .hasPrivious(pageData.hasPrevious())
                .totalPages(pageData.getTotalPages())
                .contents(pageData.getContent())
                .totalElements(pageData.getTotalElements())
                .build();
    }

    public void changeReview(Long reviewId, RegisterReviewRequest registerReviewRequest) {
        User user = validUser(AuthHolder.getUserId());
        Review review = validReview(reviewId);

        validRegisterUser(user.getId(), review.getUser().getId());
        review.setTitle(registerReviewRequest.getTitle());
        review.setContents(registerReviewRequest.getContents());
        review.setPhrase(registerReviewRequest.getPhrase());
        reviewRepository.save(review);

    }

    public void deleteReview(Long reviewId) {
        User user = validUser(AuthHolder.getUserId());
        Review review = validReview(reviewId);

        validRegisterUser(user.getId(), review.getUser().getId());
        reviewRepository.updateIsDeletedById(reviewId);
    }

    public DetailReviewResponse getDetailReview(Long reviewId) {
        Review review = validReview(reviewId);
        return DetailReviewResponse.from(review);
    }

    private User validUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
    }

    private Book validBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new CustomException(BookErrorCode.NOT_FOUND_BOOK));
    }

    private Review validReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ReviewErrorCode.NOT_FOUND_REVIEW));
    }

    private void validRegisterUser(Long userId, Long reviewRegisterUserId) {
        if (!Objects.equals(userId, reviewRegisterUserId)) {
            throw new CustomException(ReviewErrorCode.FORBIDDEN_REVIEW);
        }
    }
}

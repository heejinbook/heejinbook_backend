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
import com.book.heejinbook.repository.LikeRepository;
import com.book.heejinbook.repository.book.BookRepository;
import com.book.heejinbook.repository.comment.CommentRepository;
import com.book.heejinbook.repository.review.ReviewCustomRepositoryImpl;
import com.book.heejinbook.repository.review.ReviewRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.security.AuthHolder;
import com.book.heejinbook.utils.PaginationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewCustomRepositoryImpl reviewCustomRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    public void registerReview(Long bookId, RegisterReviewRequest registerReviewRequest) {

        User user = validUser(AuthHolder.getUserId());
        Book book = validBook(bookId);
        validRating(registerReviewRequest.getRating());
        if (reviewRepository.existsByBookAndUserAndIsDeletedFalse(book,user)) {
            throw new CustomException(ReviewErrorCode.ALREADY_WRITTEN_REVIEW);
        }

        reviewRepository.save(Review.from(registerReviewRequest, book, user));
    }

    public List<ReviewSwiperResponse> getSwiperList(Long bookId, Integer size) {
        Book book = validBook(bookId);
        User user = validUser(AuthHolder.getUserId());
        return reviewCustomRepository.findBestReviewList(book, user, size);
    }

    public PaginationResponse<ReviewListResponse> getList(Long bookId, Pageable pageable, String sort) {
        Book book = validBook(bookId);
        Page<ReviewListResponse> pageData = reviewCustomRepository.findReviewList(book, pageable, sort);

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
        validRating(registerReviewRequest.getRating());
        review.setTitle(registerReviewRequest.getTitle());
        review.setContents(registerReviewRequest.getContents());
        review.setPhrase(registerReviewRequest.getPhrase());
        review.setRating(registerReviewRequest.getRating());
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
        User user = validUser(AuthHolder.getUserId());
       return reviewCustomRepository.findDetailReviewByReviewId(review, user);
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

    private void validRating(Integer rating) {
        if (!(rating > 0 && rating <= 5)) {
            throw new CustomException(ReviewErrorCode.BAD_REQUEST_RATING_VALUE);
        }
    }
}

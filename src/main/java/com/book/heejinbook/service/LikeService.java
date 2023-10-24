package com.book.heejinbook.service;

import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Like;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;
import com.book.heejinbook.error.domain.LikeErrorCode;
import com.book.heejinbook.error.domain.ReviewErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.LikeRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.repository.review.ReviewRepository;
import com.book.heejinbook.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    @Transactional
    public Void doLike(Long reviewId) {

        User user = validUser(AuthHolder.getUserId());
        Review review = validReview(reviewId);
        validSelfLike(user.getId(), review.getUser().getId());

        if (likeRepository.existsByUserAndReview(user, review)) {
            likeRepository.deleteByUserAndReview(user, review);
        } else {
            likeRepository.save(Like.builder()
                            .user(user)
                            .review(review)
                    .build());
        }
        return null;
    }

    private User validUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
    }

    private Review validReview(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(ReviewErrorCode.NOT_FOUND_REVIEW));
    }

    private void validSelfLike(Long userId, Long reviewRegisterId) {
        if (Objects.equals(userId, reviewRegisterId)) {
            throw new CustomException(LikeErrorCode.NOT_SUPPOSED_SELF_LIKE);
        }
    }
}

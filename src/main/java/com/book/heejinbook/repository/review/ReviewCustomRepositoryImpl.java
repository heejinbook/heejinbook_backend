package com.book.heejinbook.repository.review;

import com.book.heejinbook.dto.review.response.DetailReviewResponse;
import com.book.heejinbook.dto.review.response.MyReviewResponse;
import com.book.heejinbook.dto.review.response.ReviewListResponse;
import com.book.heejinbook.dto.review.response.ReviewSwiperResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Review;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.enums.ReviewSortType;
import com.book.heejinbook.security.AuthHolder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.book.heejinbook.entity.QReview.review;
import static com.book.heejinbook.entity.QComment.comment;
import static com.book.heejinbook.entity.QLike.like;
import static com.book.heejinbook.entity.QUser.user;

@Repository
@RequiredArgsConstructor
public class ReviewCustomRepositoryImpl implements ReviewCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ReviewListResponse> findReviewList(Book book, Pageable pageable, String sort) {

        ReviewSortType reviewSortType = ReviewSortType.fromString(sort);
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        if (Objects.requireNonNull(reviewSortType) == ReviewSortType.COUNT_LIKE) {
            orderSpecifiers.add(like.id.countDistinct().desc());
        }

        if (Objects.requireNonNull(reviewSortType) == ReviewSortType.RATING_DESC) {
            orderSpecifiers.add(review.rating.desc());
        }

        if (Objects.requireNonNull(reviewSortType) == ReviewSortType.COUNT_COMMENT) {
            orderSpecifiers.add(comment.id.countDistinct().desc());
        }

        orderSpecifiers.add(review.id.desc());

        List<ReviewListResponse> result = jpaQueryFactory
                .select(Projections.constructor(ReviewListResponse.class,
                        review.id,
                        review.user.nickname,
                        review.user.profileUrl,
                        review.title,
                        review.contents,
                        review.createdAt,
                        review.phrase,
                        like.id.countDistinct(),
                        comment.id.countDistinct(),
                        JPAExpressions.select(Expressions.constant(true))
                                .from(like)
                                .where(like.review.id.eq(review.id)
                                        .and(like.user.id.eq(AuthHolder.getUserId())))
                                .exists(),
                        review.rating
                ))
                .from(review)
                .leftJoin(like)
                .on(like.review.id.eq(review.id))
                .leftJoin(comment)
                .on(comment.review.id.eq(review.id),
                        comment.isDeleted.eq(false)
                        )
                .where(
                        eqBookId(book.getId()),
                        isDeletedFalse()
                )
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .groupBy(review.id)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int getTotalCount = jpaQueryFactory
                .select(Wildcard.count)
                .from(review)
                .leftJoin(like)
                .on(like.review.id.eq(review.id))
                .leftJoin(comment)
                .on(comment.review.id.eq(review.id),
                        comment.isDeleted.eq(false)
                )
                .where(
                        eqBookId(book.getId()),
                        isDeletedFalse()
                )
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .groupBy(review)
                .fetch().size();

        return new PageImpl<>(result, pageable, getTotalCount);
    }

    @Override
    public List<ReviewSwiperResponse> findBestReviewList(Book detailBook, User user, Integer size) {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        orderSpecifiers.add(like.id.count().desc());
        orderSpecifiers.add(review.id.desc());

        return jpaQueryFactory
                .select(Projections.constructor(
                                ReviewSwiperResponse.class,
                                review.id,
                                review.book.id,
                                review.user.nickname,
                                review.title,
                                review.phrase,
                                review.user.profileUrl,
                                review.contents,
                                review.createdAt,
                                like.id.countDistinct(),
                                comment.id.countDistinct(),
                                JPAExpressions.select(Expressions.constant(true))
                                        .from(like)
                                        .where(like.review.id.eq(review.id)
                                                .and(like.user.id.eq(AuthHolder.getUserId())))
                                        .exists(),
                                review.rating
                ))
                .from(review)
                .leftJoin(like)
                .on(like.review.id.eq(review.id))
                .leftJoin(comment)
                .on(comment.review.id.eq(review.id),
                        comment.isDeleted.eq(false)
                )
                .where(
                        eqBookId(detailBook.getId()),
                        isDeletedFalse()
                )
                .limit(size)
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .groupBy(review.id)
                .fetch();
    }

    @Override
    public DetailReviewResponse findDetailReviewByReviewId(Review detailReview, User loginUser) {
        DetailReviewResponse detailReviewResponse = jpaQueryFactory
                .select(Projections.constructor(DetailReviewResponse.class,
                        review.id,
                        review.user.nickname,
                        review.user.profileUrl,
                        review.title,
                        review.contents,
                        review.phrase,
                        review.createdAt,
                        review.rating,
                        like.id.countDistinct(),
                        JPAExpressions.select(Expressions.constant(true))
                                .from(like)
                                .where(like.review.id.eq(review.id)
                                        .and(like.user.eq(loginUser)))
                                .exists(),
                        JPAExpressions.select(Expressions.constant(true))
                                .from(user)
                                .where(review.user.eq(loginUser))
                                .exists(),
                        comment.id.countDistinct()
                        ))
                .from(review)
                .leftJoin(like)
                .on(like.review.eq(detailReview))
                .leftJoin(comment)
                .on(comment.review.eq(detailReview),
                        comment.isDeleted.eq(false)
                        )
                .where(
                        review.eq(detailReview)
                )
                .fetchOne();

        List<DetailReviewResponse.CommentList> commentList = jpaQueryFactory
                .select(Projections.constructor(DetailReviewResponse.CommentList.class,
                        comment.id,
                        comment.review.id,
                        comment.contents,
                        comment.user.nickname,
                        comment.createdAt,
                        JPAExpressions.select(Expressions.constant(true))
                                .from(user)
                                .where(comment.user.eq(loginUser))
                                .exists()
                            ))
                .from(comment)
                .where(
                        comment.review.eq(detailReview),
                        comment.isDeleted.eq(false)
                )
                .fetch();
        Objects.requireNonNull(detailReviewResponse).setComments(commentList);

        return detailReviewResponse;

    }

    @Override
    public List<MyReviewResponse> findMyReviews(User my) {
        return jpaQueryFactory
                .select(Projections.constructor(MyReviewResponse.class,
                        review.id,
                        review.book.id,
                        review.book.thumbnailUrl,
                        review.book.title,
                        review.book.author,
                        review.user.nickname,
                        review.title,
                        review.phrase,
                        review.user.profileUrl,
                        review.contents,
                        review.createdAt,
                        review.rating,
                        like.id.countDistinct(),
                        comment.id.countDistinct()))
                .from(review)
                .leftJoin(like)
                .on(like.review.eq(review))
                .leftJoin(comment)
                .on(comment.review.eq(review),
                        comment.isDeleted.eq(false))
                .join(user)
                .on(user.eq(review.user))
                .join(review.book)
                .where(review.user.eq(my),
                        isDeletedFalse()
                        )
                .orderBy(review.id.desc())
                .groupBy(review.id)
                .fetch()
                ;
    }

    private BooleanExpression eqBookId(Long bookId) {
            return review.book.id.eq(bookId);
    }

    private BooleanExpression isDeletedFalse() {
        return review.isDeleted.eq(false);
    }
}

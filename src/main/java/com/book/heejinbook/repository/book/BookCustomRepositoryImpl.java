package com.book.heejinbook.repository.book;

import com.book.heejinbook.dto.book.request.BookListRequest;
import com.book.heejinbook.dto.book.response.BestBooksResponse;
import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.dto.vo.CustomPageableRequest;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.enums.BookSortType;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.book.heejinbook.entity.QBook.book;
import static com.book.heejinbook.entity.QCategory.category;
import static com.book.heejinbook.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class BookCustomRepositoryImpl implements BookCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<BookListResponse> findBookList(BookListRequest bookListRequest, Pageable pageable) {

        BookSortType bookSortType = BookSortType.fromString(bookListRequest.getSort());
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        switch (bookSortType) {
            case COUNT_REVIEW:
                orderSpecifiers.add(review.id.count().desc());
                break;
            case RATING_DESC:
                NumberTemplate<Double> avgRating = Expressions.numberTemplate(Double.class,
                        "function('ROUND', function('AVG', {0}), 1)",
                        review.rating.coalesce(0));
                orderSpecifiers.add(avgRating.desc());
                break;
            case TITLE_ASC:
                orderSpecifiers.add(book.title.asc());
                break;
        }

        orderSpecifiers.add(book.id.desc());

        List<BookListResponse> result = jpaQueryFactory
                .select(Projections.constructor(BookListResponse.class,
                            book.id,
                            book.title,
                            book.author,
                            book.thumbnailUrl,
                            review.id.count(),
                                Expressions.numberTemplate(Double.class,
                                        "function('ROUND', function('AVG', {0}), 1)",
                                        review.rating.coalesce(0))
                        ))
                .from(book)
                .leftJoin(review)
                .on(review.book.id.eq(book.id),
                        review.isDeleted.eq(false))
                .where(
                        eqCategoryId(bookListRequest.getCategory()),
                        containsSearchKeyword(bookListRequest.getSearchKeyword())
                )
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .groupBy(book)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int getTotalCount = jpaQueryFactory
                .select(Wildcard.count)
                .from(book)
                .leftJoin(review)
                .on(review.book.id.eq(book.id),
                        review.isDeleted.eq(false))
                .where(
                        eqCategoryId(bookListRequest.getCategory()),
                        containsSearchKeyword(bookListRequest.getSearchKeyword())
                )
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .groupBy(book)
                .fetch().size();

        return new PageImpl<>(result, pageable, getTotalCount);
    }

    @Override
    public List<BestBooksResponse> findBestBookList() {

        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        NumberTemplate<Double> avgRating = Expressions.numberTemplate(Double.class,
                "function('ROUND', function('AVG', {0}), 1)",
                review.rating.coalesce(0));
        orderSpecifiers.add(avgRating.desc());
        orderSpecifiers.add(book.id.desc());

        return jpaQueryFactory
                .select(Projections.constructor(BestBooksResponse.class,
                        book.id,
                        book.title,
                        book.author,
                        book.thumbnailUrl,
                        review.id.count(),
                        Expressions.numberTemplate(Double.class,
                                "function('ROUND', function('AVG', {0}), 1)",
                                review.rating.coalesce(0)),
                        book.description
                        ))
                .from(book)
                .leftJoin(review)
                .on(review.book.eq(book),
                    review.isDeleted.eq(false)
                )
                .orderBy(orderSpecifiers.toArray(new OrderSpecifier[0]))
                .groupBy(book)
                .limit(3)
                .fetch();
    }

    @Override
    public Boolean existBestBooks(Book detailBook) {

        List<BestBooksResponse> bestBookList = findBestBookList();

        return bestBookList.stream().anyMatch(bestBooksResponse -> Objects.equals(bestBooksResponse.getBookId(), detailBook.getId()));
    }

    private BooleanExpression eqCategoryId(Long categoryId) {
        if (categoryId != null && categoryId > 0) {
            return book.category.id.eq(categoryId);
        }
        return null;
    }

    private BooleanExpression containsSearchKeyword(String searchKeyword) {
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            return book.title.contains(searchKeyword)
                    .or(book.author.contains(searchKeyword));
        }
        // If searchKeyword is null or empty, return always true to match all records
        return null;
    }



}

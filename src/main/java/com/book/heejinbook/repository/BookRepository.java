package com.book.heejinbook.repository;

import com.book.heejinbook.dto.book.request.BookListRequest;
import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.book.heejinbook.dto.book.response.BookListResponse " +
            "(b.id, b.title, b.author, b.thumbnailUrl, 0) " +
            "FROM Book b " +
            "JOIN b.category c " +
            "WHERE (:#{#request.category} = 0L OR :#{#request.category} IS NULL OR c.id = :#{#request.category}) " +
            "AND (:#{#request.searchKeyword} IS NULL OR LOWER(b.title) LIKE CONCAT('%', LOWER(:#{#request.searchKeyword}), '%')) "
    )
    Page<BookListResponse> findByBookList(
            @Param("request")BookListRequest bookListRequest,
            Pageable pageable
            );

}

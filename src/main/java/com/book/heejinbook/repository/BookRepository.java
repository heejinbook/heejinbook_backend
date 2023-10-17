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
            "(b.id, b.title, b.author, b.thumbnailUrl, COUNT(r)) " +
            "FROM Book b " +
            "JOIN b.category c " +
            "LEFT OUTER JOIN Review r " +
            "ON r.book = b " +
            "AND r.isDeleted = false " +
            "WHERE (:#{#request.category} = 0L OR :#{#request.category} IS NULL OR c.id = :#{#request.category}) " +
            "AND (:#{#request.searchKeyword} IS NULL OR LOWER(b.title) LIKE CONCAT('%', LOWER(:#{#request.searchKeyword}), '%') OR LOWER(b.author) LIKE CONCAT('%', LOWER(:#{#request.searchKeyword}), '%')) " +
            "GROUP BY b.id, b.title, b.author, b.thumbnailUrl"
    )
    Page<BookListResponse> findByBookList(
            @Param("request")BookListRequest bookListRequest,
            Pageable pageable
            );

}

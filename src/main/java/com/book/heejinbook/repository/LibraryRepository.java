package com.book.heejinbook.repository;

import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Library;
import com.book.heejinbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryRepository extends JpaRepository<Library, Long> {
    long deleteByBookAndUser(Book book, User user);
    boolean existsByBookAndUser(Book book, User user);
}

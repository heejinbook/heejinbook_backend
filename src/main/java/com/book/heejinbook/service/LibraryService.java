package com.book.heejinbook.service;

import com.book.heejinbook.dto.library.response.MyLibraryListResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.Library;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;
import com.book.heejinbook.error.domain.LibraryErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.BookRepository;
import com.book.heejinbook.repository.LibraryRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;


    @Transactional
    public void postLibrary(Long bookId) {

        User user = validUser(AuthHolder.getUserId());
        Book book = validBook(bookId);

        if (libraryRepository.existsByBookAndUser(book, user)) {
            libraryRepository.deleteByBookAndUser(book, user);
        } else {
            libraryRepository.save(Library.builder()
                            .user(user)
                            .book(book)
                    .build());
        }
    }

    @Transactional
    public void deleteLibrary(Long bookId) {

        User user = validUser(AuthHolder.getUserId());
        Book book = validBook(bookId);

        if (!libraryRepository.existsByBookAndUser(book, user)) {
            throw new CustomException(LibraryErrorCode.NOT_FOUND_BOOK);
        }
        libraryRepository.deleteByBookAndUser(book, user);
    }

    public List<MyLibraryListResponse> getMyLibraryList() {

        User user = validUser(AuthHolder.getUserId());
        List<Library> libraries = libraryRepository.findAllByUser(user);
        return libraries.stream().map(MyLibraryListResponse::from).collect(Collectors.toList());
    }


    private User validUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
    }

    private Book validBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new CustomException(BookErrorCode.NOT_FOUND_BOOK));
    }
}

package com.book.heejinbook.service;

import com.book.heejinbook.dto.book.request.BookListRequest;
import com.book.heejinbook.dto.book.request.KakaoBookDataRequest;
import com.book.heejinbook.dto.book.response.BestBooksResponse;
import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.dto.book.response.DetailBookResponse;
import com.book.heejinbook.dto.book.response.KakaoBookResponse;
import com.book.heejinbook.dto.vo.CustomPageableRequest;
import com.book.heejinbook.dto.vo.PaginationResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.entity.User;
import com.book.heejinbook.error.CustomException;
import com.book.heejinbook.error.domain.BookErrorCode;
import com.book.heejinbook.error.domain.UserErrorCode;
import com.book.heejinbook.repository.book.BookCustomRepositoryImpl;
import com.book.heejinbook.repository.book.BookRepository;
import com.book.heejinbook.repository.LibraryRepository;
import com.book.heejinbook.repository.review.ReviewRepository;
import com.book.heejinbook.repository.UserRepository;
import com.book.heejinbook.security.AuthHolder;
import com.book.heejinbook.utils.PaginationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;
    private final BookCustomRepositoryImpl bookCustomRepository;

    @Value("${kakao.kakaoClientId}")
    private String kakaoClientId;

    public KakaoBookResponse insertData(KakaoBookDataRequest kakaoBookDataRequest) {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "KakaoAK "+kakaoClientId);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<KakaoBookResponse> response = restTemplate.exchange("https://dapi.kakao.com/v3/search/book?query="+kakaoBookDataRequest.getQuery()+"&size=50", HttpMethod.GET, entity, KakaoBookResponse.class);

        KakaoBookResponse kakaoBookResponse = response.getBody();
        List<Book> insertData = kakaoBookResponse.getDocuments().stream().map(Book::from).collect(Collectors.toList());
        bookRepository.saveAll(insertData);
        return kakaoBookResponse;
    }

    public PaginationResponse<BookListResponse> getList(BookListRequest bookListRequest, Pageable pageable) {

        if (bookListRequest.getSearchKeyword() != null) {
            bookListRequest.setSearchKeyword(URLDecoder.decode(bookListRequest.getSearchKeyword(), StandardCharsets.UTF_8));
        }

        Page<BookListResponse> pageData = bookCustomRepository.findBookList(bookListRequest, pageable);

        return new PaginationBuilder<BookListResponse>()
                .hasNext(pageData.hasNext())
                .hasPrivious(pageData.hasPrevious())
                .totalPages(pageData.getTotalPages())
                .contents(pageData.getContent())
                .totalElements(pageData.getTotalElements())
                .build();

    }


    public DetailBookResponse getDetail(Long bookId) {

        Book book = validBook(bookId);
        User user = validUser(AuthHolder.getUserId());
        Long reviewCount = reviewRepository.countByBook(book);
        Double avgRating = reviewRepository.avgRatingByBook(book);
        Boolean isLibrary = libraryRepository.existsByBookAndUser(book, user);
        Boolean isWriteReview = reviewRepository.existsByBookAndUserAndIsDeletedFalse(book, user);
        Boolean isBest = bookCustomRepository.existBestBooks(book);
        return DetailBookResponse.of(book, reviewCount, isLibrary, isWriteReview, avgRating, isBest);
    }

    public List<BestBooksResponse> getBestBooks() {
        return bookCustomRepository.findBestBookList();
    }

    private Book validBook(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new CustomException(BookErrorCode.NOT_FOUND_BOOK));
    }

    private User validUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException(UserErrorCode.NOT_FOUND_USER));
    }
}

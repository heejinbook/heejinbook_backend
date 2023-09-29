package com.book.heejinbook.service;

import com.book.heejinbook.dto.book.request.BookListRequest;
import com.book.heejinbook.dto.book.request.KakaoBookDataRequest;
import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.dto.book.response.KakaoBookResponse;
import com.book.heejinbook.dto.vo.PaginationResponse;
import com.book.heejinbook.entity.Book;
import com.book.heejinbook.repository.BookRepository;
import com.book.heejinbook.utils.PaginationBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

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

        Page<BookListResponse> pageData = bookRepository.findByBookList(bookListRequest, pageable);

        return new PaginationBuilder<BookListResponse>()
                .hasNext(pageData.hasNext())
                .hasPrivious(pageData.hasPrevious())
                .totalPages(pageData.getTotalPages())
                .contents(pageData.getContent())
                .totalElements(pageData.getTotalElements())
                .build();

    }
}

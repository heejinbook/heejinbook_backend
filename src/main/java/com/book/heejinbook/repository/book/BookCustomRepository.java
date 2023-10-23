package com.book.heejinbook.repository.book;

import com.book.heejinbook.dto.book.request.BookListRequest;
import com.book.heejinbook.dto.book.response.BookListResponse;
import com.book.heejinbook.dto.vo.CustomPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCustomRepository {

    Page<BookListResponse> findBookList(BookListRequest bookListRequest, Pageable pageable);

}

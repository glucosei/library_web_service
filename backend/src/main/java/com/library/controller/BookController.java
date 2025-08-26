package com.library.controller;

import com.library.dto.BookResponse;
import com.library.service.AuthService;
import com.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * 도서 검색 및 대출 관련 API 요청을 처리하는 컨트롤러.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * 도서 검색 요청을 처리합니다.
     *
     * @param keyword 검색 키워드
     * @return 검색 결과 도서 목록
     */
    @GetMapping
    public ResponseEntity<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        List<BookResponse> books = bookService.searchBooks(keyword);

        return ResponseEntity.ok(books);
    }
}
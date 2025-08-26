package com.library.service;

import com.library.domain.Book;
import com.library.dto.BookResponse;
import com.library.repository.BookRepository;
import com.library.repository.RealBookRepository;
import com.library.domain.enums.Status; // Status enum 추가
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final RealBookRepository realBookRepository; // RealbookRepository 주입


    public List<BookResponse> searchBooks(String keyword) {
        // 1. 키워드로 도서(Book) 엔티티 목록을 가져옵니다.
        List<Book> books = bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword);

        // 2. 각 도서에 대해 대출 가능 여부를 확인하고 DTO로 변환합니다.
        return books.stream()
                .map(book -> {
                    // 해당 책의 대출 가능한 재고(AVAILABLE 상태)가 있는지 확인
                    long availableCount = realBookRepository.countByBookAndStatus(book, Status.ACTIVE);
                    boolean isAvailable = availableCount > 0;



                    // BookResponse DTO에 isAvailableForBorrow 정보를 추가하여 반환
                    return new BookResponse(
                            book.getId(),
                            book.getTitle(),
                            book.getAuthor(),
                            isAvailable); // 대출 가능 여부 필드에 값 할당
                })
                .collect(Collectors.toList());
    }
}
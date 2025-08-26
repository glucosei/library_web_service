package com.library.repository;

import com.library.domain.RealBook;
import com.library.domain.Book;
import com.library.domain.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealBookRepository extends JpaRepository<RealBook, Long> {

    // 특정 책(Book)의 대출 가능한(status가 ACTIVE인) 재고 수를 셉니다.
    long countByBookAndStatus(Book book, Status status);
}
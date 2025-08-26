package com.library.repository;

import com.library.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Book 엔티티의 데이터 접근을 위한 리포지토리.
 * JpaRepository를 상속받아 기본 CRUD 메서드를 제공합니다.
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * 제목 또는 저자 이름에 특정 키워드가 포함된 도서를 검색합니다.
     * Spring Data JPA의 쿼리 메서드 기능을 사용합니다.
     *
     * @param titleKeyword 제목에 포함될 키워드
     * @param authorKeyword 저자에 포함될 키워드
     * @return 검색 조건에 맞는 Book 엔티티 리스트
     */
    List<Book> findByTitleContainingOrAuthorContaining(String titleKeyword, String authorKeyword);
}
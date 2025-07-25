package com.library.domain.mapping;

import jakarta.persistence.*;
import lombok.*;
import com.library.domain.Book;
import com.library.domain.Hashtag;
import com.library.domain.base.BaseEntity;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor


public class BookHashtag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id",nullable = false)
    private Hashtag hashtag;

}

package com.library.domain;

import jakarta.persistence.*;
import lombok.*;
import com.library.domain.base.BaseEntity;
import com.library.domain.enums.Status;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class RealBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;




    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(mappedBy = "realBook")
    private Rent rent;
}
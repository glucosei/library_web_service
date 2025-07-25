package com.library.domain;

import jakarta.persistence.*;
import lombok.*;
import com.library.domain.base.BaseEntity;
import com.library.domain.enums.AlarmKind;

import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor

public class PersonalAlarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "global_alarm_id", nullable = false)
    private GlobalAlarm globalAlarm;

    @Enumerated(EnumType.STRING)
    private AlarmKind alarmKind;


    private String title;
    private String body;
}
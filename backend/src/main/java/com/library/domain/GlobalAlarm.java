package com.library.domain;

import jakarta.persistence.*;
import lombok.*;
import com.library.domain.base.BaseEntity;
import com.library.domain.enums.AlarmKind;


import java.util.List;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor



public class GlobalAlarm extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AlarmKind alarmKind;


    private String title;
    private String body;


}

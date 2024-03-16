package com.profileeM.profileeM.notice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="notice")
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "creation_date ")
    private LocalDateTime creationDate;

    @Builder
    public Notice(String title, String content) {
        this.title = title;
        this.content = content;
        this.creationDate = LocalDateTime.now();
    }
}

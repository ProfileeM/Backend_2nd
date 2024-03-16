package com.profileeM.profileeM.group.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 필수값 누락 막음
@Table(name="\"group\"")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long groupId;

    @JoinColumn(name = "user_id")
    private Long userId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_date")
    private Date groupDate;

    private String theme;

    private String link;
}

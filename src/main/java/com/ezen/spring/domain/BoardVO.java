package com.ezen.spring.domain;

import lombok.*;

@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BoardVO {
    private long bno;
    private String title;
    private String writer;
    private String content;
    private String regDate;
}

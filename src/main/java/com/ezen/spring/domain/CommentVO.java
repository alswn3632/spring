package com.ezen.spring.domain;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CommentVO {
    private long bno;
    private long cno;
    private String writer;
    private String content;
    private String regDate;
}

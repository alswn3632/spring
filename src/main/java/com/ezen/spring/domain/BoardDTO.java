package com.ezen.spring.domain;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class BoardDTO {
    private BoardVO bvo;
    private List<FileVO> flist;
}

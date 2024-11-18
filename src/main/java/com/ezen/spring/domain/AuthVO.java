package com.ezen.spring.domain;

import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthVO {
    private int id;
    private String email;
    private String auth;
}

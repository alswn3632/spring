package com.ezen.spring.domain;

import lombok.*;

import java.util.List;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserVO {
    private String email;
    private String pwd;
    private String nickName;
    private String regDate;
    private String lastLogin;
    private List<AuthVO> authList;
}

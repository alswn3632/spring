package com.ezen.spring.service;

import com.ezen.spring.domain.UserVO;

import java.util.List;

public interface UserService {
    int register(UserVO userVO);

    List<UserVO> getList();

    int update(UserVO userVO);

    int updateHasPwd(UserVO userVO);

    int updateLastLog(String username);
}

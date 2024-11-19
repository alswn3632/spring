package com.ezen.spring.repository;

import com.ezen.spring.domain.AuthVO;
import com.ezen.spring.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int register(UserVO userVO);

    int authInsert(UserVO userVO);

    UserVO selectEmail(String username);

    List<AuthVO> selectAuth(String username);

    List<UserVO> getList();

    int update(UserVO userVO);

    int updateHasPwd(UserVO userVO);

    int updateLastLog(String username);
}

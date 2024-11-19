package com.ezen.spring.service;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.repository.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements UserService{
    private final UserMapper userMapper;

    @Transactional
    @Override
    public int register(UserVO userVO) {
        int isOk = userMapper.register(userVO);
        if(isOk>0){
            isOk *= userMapper.authInsert(userVO);
        }
        return isOk;
    }

    @Override
    public List<UserVO> getList() {
        List<UserVO> list = userMapper.getList();
        for(UserVO userVO : list){
            userVO.setAuthList(userMapper.selectAuth(userVO.getEmail()));
        }
        return list;
    }

    @Override
    public int update(UserVO userVO) {
        return userMapper.update(userVO);
    }

    @Override
    public int updateHasPwd(UserVO userVO) {
        return userMapper.updateHasPwd(userVO);
    }

    @Override
    public int updateLastLog(String username) {
        return userMapper.updateLastLog(username);
    }
}

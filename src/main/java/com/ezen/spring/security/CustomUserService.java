package com.ezen.spring.security;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.repository.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
public class CustomUserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username 주고 UserVO 객체 리턴 (authList도 같이 포함해서@!)
        UserVO userVO = userMapper.selectEmail(username);
        userVO.setAuthList(userMapper.selectAuth(username));
        // userDetails 값으로 리턴되도록 다듬어야함
        return new AuthUser(userVO);
    }
}

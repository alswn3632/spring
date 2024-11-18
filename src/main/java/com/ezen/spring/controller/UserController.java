package com.ezen.spring.controller;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/user/*")
@Controller
public class UserController {
    private final UserService usv;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(){
        return "/user/register";
    }

    @GetMapping("/login")
    public String login(){
        return "/user/login";
    }

    @PostMapping("/register")
    public String register(UserVO userVO){
        log.info(">>>> userVO > {}", userVO);
        // 비밀번호 암호화
        userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));
        int isOk = usv.register(userVO);
        return "/index";
    }
}

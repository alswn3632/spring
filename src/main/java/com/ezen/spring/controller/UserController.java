package com.ezen.spring.controller;

import com.ezen.spring.domain.UserVO;
import com.ezen.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    @GetMapping("/list")
    public String list(Model m){
        List<UserVO> list = usv.getList();
        log.info(">>>> user list > {}", list);
        m.addAttribute("list", list);

        return "/user/list";
    }

    @GetMapping("/modify")
    public String modify(){

        return "/user/modify";
    }

    @PostMapping("/modify")
    public String modify(UserVO userVO){
        int isOk;
        if(userVO.getPwd() == null || userVO.getPwd().equals("")){
            isOk = usv.update(userVO);
        }else{
            userVO.setPwd(passwordEncoder.encode(userVO.getPwd()));
            isOk = usv.updateHasPwd(userVO);
        }
        log.info(">>>> user update > {}",(isOk>0? "성공":"실패"));

        return "redirect:/user/logout";

    }

    @GetMapping("/updateLastLog")
    public String updateLastLog(String username){
        log.info(">>>> username > {}", username);
        int isOk = usv.updateLastLog(username);

        return "redirect:/user/logout";
    }


}

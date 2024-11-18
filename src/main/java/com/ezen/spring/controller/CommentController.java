package com.ezen.spring.controller;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment/*")
@RestController
public class CommentController {
    private final CommentService csv;

    @ResponseBody
    @PostMapping("/register")
    public String register(@RequestBody CommentVO commentVO){
        log.info(">>>> commentVO > {}", commentVO);
        int isOk = csv.register(commentVO);
        return isOk>0? "1" : "0";
    }

    @ResponseBody
    @GetMapping(value = "list/{bno}/{page}", produces = MediaType.APPLICATION_JSON_VALUE)
    public PagingHandler getList(@PathVariable("bno") long bno, @PathVariable("page") int page){
        PagingVO pgvo = new PagingVO(page, 5);
        PagingHandler ph = csv.getList(bno, pgvo);
        return ph;
    }

    @ResponseBody
    @DeleteMapping(value = "/delete/{cno}")
    public String delete(@PathVariable("cno") long cno){
        int isOk = csv.delete(cno);
        return isOk>0? "1" : "0";
    }

    @ResponseBody
    @PutMapping("/modify")
    public String modify(@RequestBody CommentVO commentVO){
        log.info(">>>> commentVO > {}", commentVO);
        int isOk = csv.modify(commentVO);
        return isOk>0? "1" : "0";
    }



}

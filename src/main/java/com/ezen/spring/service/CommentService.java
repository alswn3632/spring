package com.ezen.spring.service;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;

import java.util.List;

public interface CommentService {
    int register(CommentVO commentVO);

    PagingHandler getList(long bno, PagingVO pgvo);

    int delete(long cno);

    int modify(CommentVO commentVO);
}

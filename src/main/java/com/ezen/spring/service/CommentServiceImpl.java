package com.ezen.spring.service;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.repository.CommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommentServiceImpl implements CommentService{
    private final CommentMapper commentMapper;

    @Override
    public int register(CommentVO commentVO) {
        return commentMapper.register(commentVO);
    }

    @Override
    public PagingHandler getList(long bno, PagingVO pgvo) {
        List<CommentVO> cmtList = commentMapper.getList(bno,pgvo);
        int totalCount = commentMapper.getTotalCount(bno);
        PagingHandler ph = new PagingHandler(totalCount, pgvo, cmtList);
        return ph;
    }

    @Override
    public int delete(long cno) {
        return commentMapper.delete(cno);
    }

    @Override
    public int modify(CommentVO commentVO) {
        return commentMapper.modify(commentVO);
    }
}

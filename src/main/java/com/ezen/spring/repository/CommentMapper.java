package com.ezen.spring.repository;

import com.ezen.spring.domain.CommentVO;
import com.ezen.spring.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    int register(CommentVO commentVO);

    List<CommentVO> getList(@Param("bno") long bno, @Param("pgvo") PagingVO pgvo);

    int getTotalCount(long bno);

    int delete(long cno);

    int modify(CommentVO commentVO);
}

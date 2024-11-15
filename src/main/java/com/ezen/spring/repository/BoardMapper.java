package com.ezen.spring.repository;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int register(BoardVO boardVO);

    int insertFile(BoardDTO boardDTO);

    List<BoardVO> getList(PagingVO pgvo);

    BoardVO getDetail(long bno);

    int modify(BoardVO boardVO);

    int delete(long bno);

    int getTotalCount(PagingVO pgvo);

    long getBno();
}

package com.ezen.spring.service;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.repository.BoardMapper;
import com.ezen.spring.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardServiceImpl implements BoardService{
    private final BoardMapper boardMapper;
    private final FileMapper fileMapper;

    @Transactional
    @Override
    public int register(BoardDTO boardDTO) {
        int isOk = boardMapper.register(boardDTO.getBvo());

        if(boardDTO.getFlist() == null){
            return isOk;
        }

        if(isOk > 0 && !boardDTO.getFlist().isEmpty()){
            long bno = boardMapper.getBno();
            for(FileVO fvo : boardDTO.getFlist()){
                fvo.setBno(bno);
                isOk *= fileMapper.insertFile(fvo);
            }
        }

        return isOk;
    }

    @Override
    public List<BoardVO> getList(PagingVO pgvo) {
        return boardMapper.getList(pgvo);
    }

    @Transactional
    @Override
    public BoardDTO getDetail(long bno) {
        BoardVO bvo = boardMapper.getDetail(bno);
        List<FileVO> flist = fileMapper.getFileList(bno);
        BoardDTO boardDTO = new BoardDTO(bvo, flist);

        return boardDTO;
    }

    @Transactional
    @Override
    public int modify(BoardDTO boardDTO) {
        int isOk = boardMapper.modify(boardDTO.getBvo());

        if(boardDTO.getFlist() == null){
            return isOk;
        }

        if(isOk > 0 && !boardDTO.getFlist().isEmpty()){
            for(FileVO fvo : boardDTO.getFlist()){
                fvo.setBno(boardDTO.getBvo().getBno());
                isOk *= fileMapper.insertFile(fvo);
            }
        }
        return isOk;
    }

    @Override
    public int delete(long bno) {
        return boardMapper.delete(bno);
    }

    @Override
    public int getTotalCount(PagingVO pgvo) {
        return boardMapper.getTotalCount(pgvo);
    }

    @Override
    public int deleteFile(String uuid) {
        return fileMapper.deleteFile(uuid);
    }

    @Override
    public FileVO getFile(String uuid) {
        return fileMapper.getFile(uuid);
    }
}

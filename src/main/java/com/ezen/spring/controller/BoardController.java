package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {
    private final BoardService bsv;

    @GetMapping("/register")
    public String register(){
        return "board/register";
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO){
        log.info(">>>> boardVO > {}", boardVO);
        int isOk = bsv.register(boardVO);
        return "index";
    }

    @GetMapping("/list")
    public String list(Model m, PagingVO pgvo){
        // 전체 게시글 수, pgvo를 파라미터로 ph 객체 생성
        int totalCount = bsv.getTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);
        m.addAttribute("ph", ph);

        // 설정된 범위의 리스트를 가져옴
        List<BoardVO> list = bsv.getList(pgvo);
        m.addAttribute("list", list);

        return "board/list";
    }

    @GetMapping("/detail")
    public String detail(Model m, long bno){
        BoardVO boardVO = bsv.getDetail(bno);
        m.addAttribute("boardVO", boardVO);
        return "board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO boardVO, RedirectAttributes redirectAttributes){
        int isOk = bsv.modify(boardVO);
        log.info(">>>> board update > {}", (isOk>0? "성공":"실패"));
        redirectAttributes.addAttribute("bno", boardVO.getBno());
        return "redirect:/board/detail";
    }

    @GetMapping("/delete")
    public String delete(long bno){
        int isOk = bsv.delete(bno);
        log.info(">>>> board delete > {}", (isOk>0? "성공":"실패"));
        return "redirect:/board/list";
    }

}

package com.ezen.spring.controller;

import com.ezen.spring.domain.BoardDTO;
import com.ezen.spring.domain.BoardVO;
import com.ezen.spring.domain.FileVO;
import com.ezen.spring.domain.PagingVO;
import com.ezen.spring.handler.FileHandler;
import com.ezen.spring.handler.FileRemoveHandler;
import com.ezen.spring.handler.PagingHandler;
import com.ezen.spring.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/board/*")
@RequiredArgsConstructor
@Slf4j
@Controller
public class BoardController {
    private final BoardService bsv;
    private final FileHandler fh;

    @GetMapping("/register")
    public String register(){
        return "board/register";
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO, @RequestParam(name="files", required = false)MultipartFile[] files){
        log.info(">>>> boardVO > {}", boardVO);
        log.info(">>>> isEmpty file > {}", files[0].isEmpty());
        // 파일 첨부하지 않았을 때 >>>> isEmpty file > true

        List<FileVO> flist = null;
        if(files[0].getSize() > 0){
            flist = fh.uploadFiles(files);
            log.info(">>>> FileHandler On! > {}", flist);
        }
        int isOk = bsv.register(new BoardDTO(boardVO, flist));
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
        BoardDTO boardDTO = bsv.getDetail(bno);
        m.addAttribute("boardDTO", boardDTO);
        return "board/detail";
    }

    @PostMapping("/modify")
    public String modify(BoardVO boardVO, @RequestParam(name="files", required = false)MultipartFile[] files, RedirectAttributes redirectAttributes){
        List<FileVO> flist = null;
        if(files[0].getSize() > 0){
            log.info(">>>> error > {}", files[0]);
            flist = fh.uploadFiles(files);
            log.info(">>>> FileHandler On! > {}", flist);
        }

        int isOk = bsv.modify(new BoardDTO(boardVO, flist));
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

    @ResponseBody
    @DeleteMapping(value="/file/{uuid}")
    public String fileDelete(@PathVariable("uuid") String uuid) {
        FileRemoveHandler fr = new FileRemoveHandler();

        FileVO fvo = bsv.getFile(uuid);

        int isOk = fr.deleteFile(fvo);
        if(isOk > 0){
            isOk = bsv.deleteFile(uuid);
        }

        log.info(">>>> delete uuid > {}", (isOk>0? "성공" : "실패"));

        return isOk>0? "1" : "0";
    }

}

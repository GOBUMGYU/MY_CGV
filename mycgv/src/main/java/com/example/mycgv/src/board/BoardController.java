package com.example.mycgv.src.board;

import com.example.mycgv.src.FileServiceImpl;
import com.example.mycgv.src.PageServiceImpl;
import com.example.mycgv.src.board.model.PostBoardReq;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final PageServiceImpl pageService;
    private final FileServiceImpl fileService;

    /**
     * /board/content 게시판 상세페이지
     */
    @GetMapping("/content")
    public ModelAndView boardContent(@RequestParam(value = "bid", required = false) Long bid) {
        ModelAndView mv = new ModelAndView();

        PostBoardReq vo = boardService.Content(bid);
        if(vo != null){
            boardService.getUpdateHits(bid);
        }

        mv.addObject("vo", vo);
        System.out.println("");
        System.out.println("vo.toString() = " + vo.toString());
        System.out.println("");
        mv.setViewName("/board/boardContent");

        return mv;
    }

    /**
     * /board 게시판 리스트 Form
     */
    @GetMapping("")
    public ModelAndView boardListForm(String rpage) {
        ModelAndView mv = new ModelAndView();

        Map<String, Integer> param = pageService.getPageResult(rpage, "board", boardService);
        List<PostBoardReq> list = boardService.boardList(param.get("startCount"), param.get("endCount"));
        mv.addObject("list", list);
        mv.addObject("dbCount", param.get("dbCount"));
        mv.addObject("pageSize", param.get("pageSize"));
        mv.addObject("rpage", param.get("rpage"));
        mv.setViewName("/board/boardList");
        log.info("list : {}", list);
        return mv;
    }

    /**
     * /board/write 게시판 글쓰기 Form
     */
    @GetMapping("/write")
    public String boardWriteForm() {
        return "board/boardWrite";
    }

    /**
     * /board/write 게시판 글쓰기
     */
    @PostMapping("/write")
    public ModelAndView boardWrite(PostBoardReq postBoardReq, MultipartFile multipartFile,HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        postBoardReq = fileService.fileCheck(postBoardReq);

        int result = boardService.createBoard(postBoardReq);

        if(result == 1) {
            fileService.fileSave(postBoardReq, request);
            mv.setViewName("redirect:/board");
        }else {
            mv.setViewName("errorPage");
        }
        System.out.println("boardWrite = " + postBoardReq.toString());

        return mv;
    }

    @GetMapping("/delete")
    public ModelAndView boardDeleteForm(Long bid) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("bid", bid);
        mv.setViewName("/board/boardDelete");
        return mv;
    }

    @PostMapping("/update")
    public ModelAndView boardUpdate(PostBoardReq postBoardReq, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        String old_filename = postBoardReq.getBsfile();

        postBoardReq = fileService.updateFileCheck(postBoardReq);
        int result = boardService.update(postBoardReq);

        if(result == 1) {
            fileService.updateFileSave(postBoardReq, request, old_filename);
            mv.setViewName("redirect:/board");
        }else {
            mv.setViewName("eroor_page");
        }
        return mv;
    }

    @GetMapping("/update")
    public ModelAndView boardUpdateForm(Long bid) {
        ModelAndView mv = new ModelAndView();
        PostBoardReq vo = boardService.Content(bid);
        mv.addObject("vo", vo);
        mv.setViewName("/board/boardUpdate");
        return mv;
    }

}

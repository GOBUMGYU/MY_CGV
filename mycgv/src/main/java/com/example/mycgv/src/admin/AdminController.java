package com.example.mycgv.src.admin;

import com.example.mycgv.src.FileServiceImpl;
import com.example.mycgv.src.PageServiceImpl;
import com.example.mycgv.src.movie.Movie;
import com.example.mycgv.src.movie.MovieService;
import com.example.mycgv.src.notice.NoticeService;
import com.example.mycgv.src.notice.model.Notice;
import com.example.mycgv.src.user.UserService;
import com.example.mycgv.src.user.model.PostUserReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/admin")
public class AdminController {

    private final NoticeService noticeService;
    private final UserService userService;
    private final MovieService movieService;
    private final PageServiceImpl pageService;
    private final FileServiceImpl fileService;

    @GetMapping("")
    public String admin() {
        return "/admin/admin";
    }

    @GetMapping("/notice")
    public ModelAndView adminNoticeList(String rpage) {
        ModelAndView mv = new ModelAndView();

        Map<String, Integer> param = pageService.getPageResult(rpage, "notice", noticeService);
        List<Notice> list = noticeService.noticeList(param.get("startCount"), param.get("endCount"));

        mv.addObject("list", list);
        mv.addObject("dbCount", param.get("dbCount"));
        mv.addObject("rpage", param.get("rpage"));
        mv.addObject("pageSize", param.get("pageSize"));
        mv.setViewName("/admin/admin_notice/adminNoticeList");

        return mv;
    }

    @GetMapping("/notice/write")
    public String adminNoticeWriteForm() {
        return "/admin/admin_notice/adminNoticeWrite";
    }


    @PostMapping("/notice/write")
    public ModelAndView adminNoticeWrite(Notice vo, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        vo = fileService.fileCheck(vo);
        int result = noticeService.createNotice(vo);

        if (result == 1) {
            fileService.fileSave(vo, request);
            mv.setViewName("redirect:/admin/notice");
        } else {
            mv.setViewName("error_page");
        }
        return mv;
    }

    @GetMapping("/notice/content")
    public ModelAndView adminNoticeContent(Long nid) {
        ModelAndView mv = new ModelAndView();

        Notice vo = noticeService.content(nid);

        mv.addObject("vo", vo);
        mv.setViewName("/admin/admin_notice/adminNoticeContent");
        return mv;
    }

    @GetMapping("/notice/update")
    public ModelAndView adminNoticeUpdateForm(Long nid) {
        ModelAndView mv = new ModelAndView();

        Notice vo = noticeService.content(nid);

        mv.addObject("vo", vo);
        mv.setViewName("/admin/admin_notice/adminNoticeUpdate");

        return mv;
    }

    @PostMapping("/notice/update")
    public ModelAndView adminNoticeUpdate(Notice vo, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        String old_filename = vo.getNsfile();
        vo = fileService.updateFileCheck(vo);

        int result = noticeService.update(vo);

        if (result == 1) {
            fileService.updateFileSave(vo, request, old_filename);
            mv.setViewName("redirect:/admin/notice");
        } else {
            mv.setViewName("error_page");
        }
        return mv;
    }

    @GetMapping("/notice/delete")
    public ModelAndView adminNoticeDeleteForm(Long nid) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("nid", nid);
        mv.setViewName("/admin/admin_notice/adminNoticeDelete");

        return mv;
    }

    @PostMapping("/notice/delete")
    public ModelAndView adminNoticeDelete(Long nid, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        Notice vo = noticeService.content(nid);
        int result = noticeService.delete(nid);

        if (result == 1) {
            fileService.fileDelete(vo, request);
            mv.setViewName("redirect:/admin/notice");
        } else {
            mv.setViewName("error_page");
        }
        return mv;
    }

    @GetMapping("/member")
    public ModelAndView adminMemberListForm(String rpage) {
        ModelAndView mv = new ModelAndView();

        Map<String, Integer> param = pageService.getPageResult(rpage, "user", userService);
        List<PostUserReq> list = userService.userList(param.get("startCount"), param.get("endCount"));

        mv.addObject("list", list);
        mv.addObject("dbCount", param.get("dbCount"));
        mv.addObject("rpage", param.get("rpage"));
        mv.addObject("pageSize", param.get("pageSize"));
        mv.setViewName("/admin/admin_member/adminMemberList");

        return mv;
    }

    @GetMapping("/member/content")
    public ModelAndView adminMemberContentForm(Long idx) {
        ModelAndView mv = new ModelAndView();
        PostUserReq vo = userService.content(idx);
        String address = vo.getZonecode()+" "+vo.getAddr1()+" "+ vo.getAddr2();

        mv.addObject("vo", vo);
        mv.addObject("address", address);
        mv.setViewName("/admin/admin_member/adminMemberContent");

        return mv;
    }


    @GetMapping("/movie")
    public ModelAndView adminMovieListForm(String rpage) {
        ModelAndView mv = new ModelAndView();

        Map<String, Integer> param = pageService.getPageResult(rpage, "movie", movieService);
        List<Movie> list = movieService.movieList(param.get("startCount"), param.get("endCount"));

        mv.addObject("list", list);
        mv.addObject("dbCount", param.get("dbCount"));
        mv.addObject("rpage", param.get("rpage"));
        mv.addObject("pageSize", param.get("pageSize"));
        mv.setViewName("/admin/admin_movie/adminMovieList");

        return mv;
    }

    @GetMapping("/movie/regist")
    public ModelAndView adminMovieRegistForm() {
        ModelAndView mv = new ModelAndView();

        mv.setViewName("/admin/admin_movie/adminMovieRegist");
        return mv;
    }

    @PostMapping("/movie/regist")
    public ModelAndView adminMovieRegist(Movie movie, HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView();

        movie = fileService.multiFileCheck(movie);

        int result = movieService.createMovie(movie);

        if (result == 1) {
            Long mid = Long.valueOf(movieService.getMid());

            movie.setMid(mid);
            int result2 = movieService.getInsertFile(movie);

            if (result2 == 1) {
                fileService.multiFileSave(movie, request);
            }
        }
        mv.setViewName("redirect:/admin/movie");
        return mv;
    }

    @ResponseBody
    @GetMapping("/movie/list")
    public String movieListJson() throws JsonProcessingException {
        List<Movie> list = movieService.selectList();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.createObjectNode();
        ArrayNode listNode = mapper.createArrayNode();

        for(Movie vo:list) {
            ObjectNode movieNode = mapper.createObjectNode();
            movieNode.put("mid", vo.getMid());
            movieNode.put("mname", vo.getMname());
            movieNode.put("msfile", vo.getMsfile1());
            listNode.add(movieNode);
        }

        ((ObjectNode) rootNode).set("list", listNode);
        String jsonString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        System.out.println(jsonString);
        return jsonString;
    }

    @GetMapping("/movie/content")
    public ModelAndView adminMovieContent(Long mid) {
        ModelAndView mv = new ModelAndView();

        Movie vo = movieService.content(mid);
        mv.addObject("vo", vo);
        mv.setViewName("/admin/admin_movie/adminMovieContent");
        return mv;
    }
}

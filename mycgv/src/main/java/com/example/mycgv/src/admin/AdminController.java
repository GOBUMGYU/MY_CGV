package com.example.mycgv.src.admin;

import com.example.mycgv.src.FileServiceImpl;
import com.example.mycgv.src.PageServiceImpl;
import com.example.mycgv.src.notice.NoticeService;
import com.example.mycgv.src.notice.model.Notice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
        List<Notice> list = noticeService.noticeList(param.get("startCount"),param.get("endCount"));

        mv.addObject("list",list);
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

        if(result == 1) {
            fileService.fileSave(vo, request);
            mv.setViewName("redirect:/admin/notice");
        }else {
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

        if(result == 1) {
            fileService.updateFileSave(vo, request, old_filename);
            mv.setViewName("redirect:/admin/notice");
        }else {
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

        if(result == 1) {
            fileService.fileDelete(vo, request);
            mv.setViewName("redirect:/admin/notice");
        }else {
            mv.setViewName("error_page");
        }
        return mv;
    }

}

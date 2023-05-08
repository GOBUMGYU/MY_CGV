package com.example.mycgv.src.notice;

import com.example.mycgv.src.PageServiceImpl;
import com.example.mycgv.src.notice.model.Notice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;
    private final PageServiceImpl pageService;

    @ResponseBody
    @GetMapping("/content")
    public String noticeContent(Long nid) throws JsonProcessingException {
        Notice vo = noticeService.content(nid);

        if(vo != null) {
            noticeService.getUpdateHits(nid);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode object = objectMapper.createObjectNode();

        object.put("nid", vo.getNid());
        object.put("ntitle", vo.getNtitle());
        object.put("ncontent", vo.getNcontent());
        object.put("createAt", vo.getCreateAt());
        object.put("nhits", vo.getNhits());

        return objectMapper.writeValueAsString(object);
    }

    @ResponseBody
    @GetMapping("/list")
    public String noticeListJson(String rpage) {
        Map<String,Integer> param = pageService.getPageResult(rpage, "notice", noticeService);
        List<Notice> list = noticeService.noticeList(param.get("startCount"), param.get("endCount"));

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();

        ArrayNode array = mapper.createArrayNode();
        for(Notice vo : list) {
            ObjectNode object = mapper.createObjectNode();
            object.put("nid", vo.getNid());
            object.put("ntitle", vo.getNtitle());
            object.put("createAt", vo.getCreateAt());
            object.put("nhits", vo.getNhits());

            array.add(object);
        }
        result.set("list", array);

        result.put("dbCount", param.get("dbCount"));
        result.put("pageSize", param.get("pageSize"));
        result.put("rpage", param.get("rpage"));
        result.put("pageCount", param.get("pageCount"));

        return result.toString();
    }


    /**
     * /notice 게시판 리스트 폼
     */
    @GetMapping("")
    public String noticeListForm() {
        return "/notice/noticeList";
    }

}

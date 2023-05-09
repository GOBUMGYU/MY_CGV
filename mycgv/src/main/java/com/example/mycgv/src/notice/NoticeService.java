package com.example.mycgv.src.notice;

import com.example.mycgv.src.board.model.PostBoardReq;
import com.example.mycgv.src.notice.model.Notice;

import java.util.List;

public interface NoticeService {

    List<Notice> noticeList(int startCount, int endCount);

    int createNotice(Notice notice);

    Notice content(Long nid);

    int delete(Long nid);

    int update(Notice notice);

    void getUpdateHits(Long nid);
}

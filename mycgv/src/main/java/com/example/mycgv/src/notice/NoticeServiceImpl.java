package com.example.mycgv.src.notice;

import com.example.mycgv.src.notice.model.Notice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;


    @Override
    public List<Notice> noticeList(int startCount, int endCount) {
        return noticeRepository.noticeList(startCount, endCount);
    }

    @Override
    public int createNotice(Notice notice) {
        return noticeRepository.createNotice(notice);
    }

    @Override
    public Notice content(Long nid) {
        return noticeRepository.content(nid);
    }

    @Override
    public int delete(Long nid) {
        return noticeRepository.delete(nid);
    }

    @Override
    public int update(Notice notice) {
        return noticeRepository.update(notice);
    }

    @Override
    public void getUpdateHits(Long nid) {
        noticeRepository.getUpdateHits(nid);
    }
}

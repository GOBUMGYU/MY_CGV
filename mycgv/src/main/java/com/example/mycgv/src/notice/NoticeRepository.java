package com.example.mycgv.src.notice;

import com.example.mycgv.src.board.model.PostBoardReq;
import com.example.mycgv.src.notice.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class NoticeRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<Notice> noticeList(int startCount, int endCount) {
        String selectBoardQuery = "SELECT nid, ntitle, nhits, DATE_FORMAT(createAt, '%Y-%m-%d') AS createAt " +
                "FROM CGV.NOTICE ORDER BY createAt DESC LIMIT ? OFFSET ?";
        //Object[] selectBoardParams = new Object[] {startCount, endCount};
        Object[] selectBoardParams = new Object[]{endCount - startCount + 1, startCount - 1};
        RowMapper<Notice> rowMapper = new BeanPropertyRowMapper<>(Notice.class);
        return jdbcTemplate.query(selectBoardQuery, selectBoardParams, rowMapper);
    }

    public int createNotice(Notice notice) {
        String createNoticeQuery = "insert into CGV.NOTICE (NTITLE, NCONTENT, NFILE, NSFILE, NHITS) values (?,?,?,?,0)";
        Object[] createNoticeParams = new Object[] {
                notice.getNtitle(), notice.getNcontent(), notice.getNfile(), notice.getNsfile()
        };
        return this.jdbcTemplate.update(createNoticeQuery, createNoticeParams);
    }

    public Notice content(Long nid) {
        String noticeContentQuery = "select nid, ntitle, ncontent,nhits, createAt, nfile, nsfile " +
                "from CGV.NOTICE where nid= ?";
        Object[] noticeContentParams = new Object[] { nid };

        return this.jdbcTemplate.queryForObject(noticeContentQuery, noticeContentParams, new BeanPropertyRowMapper<>(Notice.class));
    }

    public int delete(Long nid) {
        String noticeDeleteQuery = "delete from CGV.NOTICE where nid = ?";
        Object noticeDeleteParam =  nid;
        return this.jdbcTemplate.update(noticeDeleteQuery, noticeDeleteParam);
    }


    public int update(Notice notice) {
        String noticeUpdateQuery = "update CGV.NOTICE set ntitle = ?, ncontent = ?, nfile = ?, nsfile = ? where nid = ?";
        Object[] noticeUpdateParams = new Object[] {
                notice.getNtitle(), notice.getNcontent(), notice.getNfile(), notice.getNsfile()
                , notice.getNid()
        };
        return jdbcTemplate.update(noticeUpdateQuery, noticeUpdateParams);
    }

    public void getUpdateHits(Long nid) {
        String updateHitsQuery = "UPDATE CGV.NOTICE SET nhits = nhits + 1 WHERE nid = ?";
        Object updateHitsParam = nid;
        jdbcTemplate.update(updateHitsQuery, updateHitsParam);
    }
}

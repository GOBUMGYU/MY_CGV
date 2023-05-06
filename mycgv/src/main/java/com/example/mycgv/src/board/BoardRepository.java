package com.example.mycgv.src.board;

import com.example.mycgv.src.board.model.PostBoardReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int createBoard(PostBoardReq postBoardReq) {
        String createBoardQuery = "INSERT INTO CGV.BOARD " +
                "(BTITLE, BCONTENT, BFILE, BSFILE, BHITS)" +
                " VALUES (?,?,?,?,0)";
        Object[] createBoardParams = new Object[] {
                postBoardReq.getBtitle(), postBoardReq.getBcontent(), postBoardReq.getBfile(), postBoardReq.getBsfile()
        };
        return jdbcTemplate.update(createBoardQuery, createBoardParams);
    }

    public List<PostBoardReq> boardList(int startCount, int endCount) {
        String selectBoardQuery = "SELECT bid, btitle, bhits, DATE_FORMAT(createAt, '%Y-%m-%d') AS createAt " +
                "FROM CGV.BOARD ORDER BY createAt DESC LIMIT ? OFFSET ?";
        //Object[] selectBoardParams = new Object[] {startCount, endCount};
        Object[] selectBoardParams = new Object[]{endCount - startCount + 1, startCount - 1};
        RowMapper<PostBoardReq> rowMapper = new BeanPropertyRowMapper<>(PostBoardReq.class);
        return jdbcTemplate.query(selectBoardQuery, selectBoardParams, rowMapper);
    }

    public int totalCount() {
        String boardCountQuery = "SELECT COUNT(*) FROM CGV.BOARD";
        return this.jdbcTemplate.queryForObject(boardCountQuery, Integer.class);
    }
    public void updateHits(Long bid) {
        String updateHitsQuery = "UPDATE CGV.BOARD SET bhits = bhits + 1 WHERE bid = ?";
        Object[] updateHitsParams = new Object[] {bid};
        jdbcTemplate.update(updateHitsQuery, updateHitsParams);
    }

    public PostBoardReq content(Long bid) {
        String boardContentQuery = "select bid, btitle, bcontent,bhits, createAt, bfile, bsfile " +
                "from CGV.BOARD where bid= ?";
        Object[] boardContentParams = new Object[] { bid };
        return this.jdbcTemplate.queryForObject(boardContentQuery, boardContentParams,
                new BeanPropertyRowMapper<>(PostBoardReq.class));
    }

    public int update(PostBoardReq postBoardReq) {
        String boardUpdateQuery = "update CGV.BOARD set btitle = ?, bcontent = ?, bfile = ?, bsfile = ? where bid = ?";
        Object[] boardUpdateParams = new Object[] {
                postBoardReq.getBtitle(), postBoardReq.getBcontent(), postBoardReq.getBfile(), postBoardReq.getBsfile()
                , postBoardReq.getBid()
        };
        return jdbcTemplate.update(boardUpdateQuery, boardUpdateParams);

    }

    public int delete(Long bid) {
        String boardDeleteQuery = "delete from CGV.BOARD where bid = ?";
        Object boardDeleteParam = bid;
        return jdbcTemplate.update(boardDeleteQuery, boardDeleteParam);
    }
}

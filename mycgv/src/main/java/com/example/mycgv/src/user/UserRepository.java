package com.example.mycgv.src.user;

import com.example.mycgv.src.SessionVO;
import com.example.mycgv.src.notice.model.Notice;
import com.example.mycgv.src.user.model.PostLoginReq;
import com.example.mycgv.src.user.model.PostUserReq;
import com.example.mycgv.src.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 회원가입
     */
    public int createUser(PostUserReq postUserReq) {
        String createUserQuery = "insert into CGV.USER " +
                "(id, pass, name, gender, email, zonecode, addr1, addr2, hp, pnumber, hobbylist, intro)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getId(), postUserReq.getPass(),
                postUserReq.getName(), postUserReq.getGender(),
                postUserReq.getEmail(), postUserReq.getZonecode(),
                postUserReq.getAddr1(), postUserReq.getAddr2(),
                postUserReq.getHp(), postUserReq.getPnumber(),
                postUserReq.getHobbylist(), postUserReq.getIntro()};
        return this.jdbcTemplate.update(createUserQuery, createUserParams);
    }

    /**
     * id 중복체크
     */
    public int idCheck(String id) {
        String checkIdQuery = "select count(id) from CGV.USER where id = ?";
        String checkIdParams = id;
        return this.jdbcTemplate.queryForObject(checkIdQuery, int.class, checkIdParams);
    }
  /*  public User getPwd(String id){
        String getPwdQuery = "SELECT id, pass FROM CGV.USER WHERE id = ?";
        List<User> users = jdbcTemplate.query(getPwdQuery, new Object[]{id},
                (rs, rowNum) -> new User(rs.getString("id"), rs.getString("pass")));

        return users.isEmpty() ? null : users.get(0);
    }*/

    public int totalCount() {
        String userCountQuery = "SELECT COUNT(*) FROM CGV.USER";
        return this.jdbcTemplate.queryForObject(userCountQuery, Integer.class);
    }

    public List selectAll(int startCount, int endCount) {
        String selectUserQuery = "SELECT idx, id, name, pnumber, DATE_FORMAT(createAt, '%Y-%m-%d') AS createAt " +
                "FROM CGV.USER ORDER BY createAt LIMIT ? OFFSET ?";
        Object[] selectUserParams = new Object[]{endCount - startCount + 1, startCount - 1};
        RowMapper<PostUserReq> rowMapper = new BeanPropertyRowMapper<>(PostUserReq.class);
        return jdbcTemplate.query(selectUserQuery, selectUserParams, rowMapper);
    }

    public PostUserReq selectContent(Long idx) {
        String userContentQuery = "select idx, id, name, zonecode, addr1, addr2, " +
                "pnumber , hobbylist, createAt, intro from CGV.USER where idx=?";
        Object[] userContentParam = new Object[] { idx };

        return this.jdbcTemplate.queryForObject(userContentQuery, userContentParam,
                new BeanPropertyRowMapper<>(PostUserReq.class));
    }

    public SessionVO select(PostUserReq postUserReq) {
        String userLoginQuery = "select count(*) loginresult, name, id from " +
                "CGV.USER where id=? and pass=? group by name, id";
        Object[] userLoginParams = new Object[] {
                postUserReq.getId(), postUserReq.getPass()
        };
        return this.jdbcTemplate.queryForObject(userLoginQuery, userLoginParams, new BeanPropertyRowMapper<>(SessionVO.class));
    }
}

package com.example.mycgv.src.movie;

import com.example.mycgv.src.notice.model.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MovieRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public List<Movie> movieList(int startCount, int endCount) {
        String selectMovieQuery = "SELECT mid, mcategory, mname, DATE_FORMAT(createAt, '%Y-%m-%d') AS createAt" +
                " FROM CGV.MOVIE ORDER BY createAt DESC LIMIT ? OFFSET ?";

        Object[] selectMovieParams = new Object[]{endCount - startCount + 1, startCount - 1};
        RowMapper<Movie> rowMapper = new BeanPropertyRowMapper<>(Movie.class);
        return jdbcTemplate.query(selectMovieQuery, selectMovieParams, rowMapper);
    }

    public int createMovie(Movie movie) {
        String createMovieQuery = "insert into CGV.MOVIE (MCATEGORY, MNAME, MDESC) values (?,?,?)";
        Object[] createMovieParams = new Object[] {
                movie.getMcategory(), movie.getMname(), movie.getMdesc()
        };
        return this.jdbcTemplate.update(createMovieQuery, createMovieParams);
    }

    public String selectMid() {
        String selectMovieQuery = "SELECT mid FROM CGV.MOVIE ORDER BY createAt DESC LIMIT 1";
        return jdbcTemplate.queryForObject(selectMovieQuery, String.class);
    }

    public int getInesrtFile(Movie movie) {
        String insertFileQuery = "INSERT INTO CGV.MOVIEFILE (MFILE1, MSFILE1, MFILE2, MSFILE2, MID) VALUES (?,?,?,?,?)";
        Object[] insertFileParams = new Object[] {
                movie.getMfile1(), movie.getMsfile1(), movie.getMfile2(), movie.getMsfile2()
                , movie.getMid()
        };
        return this.jdbcTemplate.update(insertFileQuery, insertFileParams);
    }

    public List<Movie> selectAll() {
        String selectMovieQuery = "SELECT cm.mid, mname, mfile1, msfile1 " +
                "FROM CGV.MOVIE cm " +
                "JOIN CGV.MOVIEFILE cmf ON cm.mid = cmf.mid " +
                "ORDER BY mid DESC LIMIT 5";

        List<Movie> list = jdbcTemplate.query(selectMovieQuery, new BeanPropertyRowMapper<>(
                Movie.class));
        return list;
    }

    public Movie content(Long mid) {
        String movieContentQuery = "SELECT cm.mid, mcategory, mname, mdesc, createAt, mfile1, msfile1, mfile2, msfile2 " +
                "FROM CGV.MOVIE cm LEFT JOIN CGV.MOVIEFILE cmf ON cm.mid = cmf.mid WHERE cm.mid = ?";
        Object[] movieContentParam = new Object[] { mid };
        return this.jdbcTemplate.queryForObject(movieContentQuery, movieContentParam, new BeanPropertyRowMapper<>(Movie.class));
    }
}

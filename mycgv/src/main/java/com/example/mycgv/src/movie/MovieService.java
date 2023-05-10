package com.example.mycgv.src.movie;

import java.util.List;

public interface MovieService {

    List<Movie> movieList(int startCount, int endCount);

    int createMovie(Movie movie);

    String getMid();

    int getInsertFile(Movie movie);

    List<Movie> selectList();

    Movie content(Long mid);
}

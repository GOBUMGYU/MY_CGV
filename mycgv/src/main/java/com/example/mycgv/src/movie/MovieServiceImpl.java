package com.example.mycgv.src.movie;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;
    @Override
    public List<Movie> movieList(int startCount, int endCount) {
        return movieRepository.movieList(startCount, endCount);
    }

    @Override
    public int createMovie(Movie movie) {
        return movieRepository.createMovie(movie);
    }

    @Override
    public String getMid() {
        return movieRepository.selectMid();
    }

    @Override
    public int getInsertFile(Movie movie) {
        return movieRepository.getInesrtFile(movie);
    }

    @Override
    public List<Movie> selectList() {
        return movieRepository.selectAll();
    }

    @Override
    public Movie content(Long mid) {
        return movieRepository.content(mid);
    }
}

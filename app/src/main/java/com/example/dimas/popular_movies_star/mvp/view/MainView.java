package com.example.dimas.popular_movies_star.mvp.view;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.mvp.BaseView;

import java.util.List;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MainView extends BaseView {


    /**
     * Menampilkan movie
     */
    void showMovie(List<Movie> movies);
}

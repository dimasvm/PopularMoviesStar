package com.example.dimas.popular_movies_star.mvp.view;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.ui.base.BaseView;

import java.util.List;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MainView extends BaseView {

    /**
     * Menampilkan loading saat memuat data movies
     */
    void showLoading(boolean b);

    /**
     * Menampilkan error saat gagal memuat data movies
     */
    void showError(String errorMessage);

    /**
     * Menampilkan movie
     */
    void showMovie(List<Movie> movies);
}

package com.example.dimas.popular_movies_star.mvp.presenter;

import android.widget.ImageView;

import com.example.dimas.popular_movies_star.OptionMovie;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.mvp.BasePresenter;
import com.example.dimas.popular_movies_star.mvp.view.MainView;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MainPresenter extends BasePresenter<MainView> {


    /**
     * Menampilkan movie page ke - n
     */
    void getPopularMovies(int page);

    /**
     * Menampilkan movie yang sedang tayang
     */
    void getNowPlaying(int currentPage);

    /**
     * Menampilkan detail movie yang di pilih
     */
    void getDetailPage(Movie movie, ImageView sharedImage);

}

package com.example.dimas.popular_movies_star.mvp.presenter;

import com.example.dimas.popular_movies_star.OptionMovie;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MainPresenter {


    /**
     * Menampilkan movie page ke - n
     */
    void getMovies(int page, OptionMovie optionMovie);

    void getNowPlaying(int currentPage);
}

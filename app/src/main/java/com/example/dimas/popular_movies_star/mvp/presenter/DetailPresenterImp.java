package com.example.dimas.popular_movies_star.mvp.presenter;

import android.support.annotation.NonNull;

import com.example.dimas.popular_movies_star.mvp.view.DetailMovieView;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailPresenterImp implements DetailPresenter {

    DetailMovieView detailMovieView;

    @NonNull
    @Override
    public void attachView(DetailMovieView view) {
        this.detailMovieView = view;
    }

    @NonNull
    @Override
    public void detachView(DetailMovieView view) {

    }
}

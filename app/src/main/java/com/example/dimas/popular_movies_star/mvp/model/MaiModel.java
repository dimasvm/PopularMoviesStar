package com.example.dimas.popular_movies_star.mvp.model;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;
import com.example.dimas.popular_movies_star.mvp.ResultListener;

import io.reactivex.Observable;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MaiModel {

    void callMatchMovies(Observable<ResponseMovie<Movie>> movieObservable, ResultListener listener);
}

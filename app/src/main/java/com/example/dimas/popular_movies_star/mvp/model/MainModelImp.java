package com.example.dimas.popular_movies_star.mvp.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;
import com.example.dimas.popular_movies_star.mvp.ResultListener;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MainModelImp implements MaiModel {

    private final String TAG = MainModelImp.class.getSimpleName();

    public MainModelImp() {
    }

    @Override
    public void callMatchMovies(@NonNull final Observable<ResponseMovie<Movie>> movieObservable, @NonNull final ResultListener<Movie> listener) {
        Log.d(TAG, "callMatchMovies: ");
        movieObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieResponseMovie -> {
                    List<Movie> movies = movieResponseMovie.getResults();
                    listener.onSuccess(movies);
                },
                        throwable -> listener.onFailure(throwable.getMessage()));
    }
}

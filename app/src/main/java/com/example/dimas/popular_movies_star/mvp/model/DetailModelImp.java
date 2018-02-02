package com.example.dimas.popular_movies_star.mvp.model;

import com.example.dimas.popular_movies_star.data.model.ResponseTrailer;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.mvp.ResultListener;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by       : dimas on 02/02/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailModelImp implements DetailModel{

    @Override
    public void callTrailerVideo(Observable<ResponseTrailer> trailerObservable, ResultListener<Trailer> listener) {
        trailerObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        responseTrailer -> listener.onSuccess(responseTrailer.getResults()),
                        throwable -> listener.onFailure(throwable.getMessage())
                );
    }
}

package com.example.dimas.popular_movies_star.mvp.model;

import com.example.dimas.popular_movies_star.data.model.ResponseTrailer;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.mvp.ResultListener;

import io.reactivex.Observable;

/**
 * Created by       : dimas on 02/02/18.
 * Email            : araymaulana66@gmail.com
 */

public interface DetailModel {

    void callTrailerVideo(Observable<ResponseTrailer> trailerObservable, ResultListener<Trailer> listener);
}

package com.example.dimas.popular_movies_star.mvp;

import com.example.dimas.popular_movies_star.data.model.Movie;

import java.util.List;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface ResultListener<T> {

    void onSuccess(List<T> listT);

    void onFailure(String errorMessage);
}

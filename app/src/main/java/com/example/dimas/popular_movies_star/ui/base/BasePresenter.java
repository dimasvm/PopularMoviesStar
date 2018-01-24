package com.example.dimas.popular_movies_star.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface BasePresenter<V extends BaseView> {

    /**
     * Untuk melekatkan view ke presenter
     */

    @NonNull
    void attachView(V view);

    /**
     * Untuk melepaskan view dari presenter
     */

    @NonNull
    void detachView(V view);
}

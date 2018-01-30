package com.example.dimas.popular_movies_star.mvp;

/**
 * Created by       : dimas on 24/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface BaseView {

    /**
     * Menampilkan loading saat memuat data movies
     */
    void showLoading(boolean b);

    /**
     * Menampilkan error saat gagal memuat data movies
     */
    void showError(String errorMessage);

}

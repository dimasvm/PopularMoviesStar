package com.example.dimas.popular_movies_star.data.network;

import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.ResponseMovie;
import com.example.dimas.popular_movies_star.data.model.ResponseTrailer;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


/**
 * Created by       : dimas on 12/01/18.
 * Email            : araymaulana66@gmail.com
 */

public interface MovieService {

    // Get Popular Movie
    @GET("movie/popular")
    Observable<ResponseMovie<Movie>> getDiscoverMovie(
            @Query("page") int page,
            @Query("sort_by") String sortBy
    );

    // Get Now Playing Movie
    @GET("movie/now_playing")
    Observable<ResponseMovie<Movie>> getNowPlaying(
            @Query("page") int page
    );

    // Get Trailer from given Movie Id
    @GET("movie/{movie_id}/videos")
    Observable<ResponseTrailer> getTrailer(
            @Path("movie_id") int id
    );

}

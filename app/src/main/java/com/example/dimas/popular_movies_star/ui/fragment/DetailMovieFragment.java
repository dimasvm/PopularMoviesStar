package com.example.dimas.popular_movies_star.ui.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.mvp.presenter.DetailPresenter;
import com.example.dimas.popular_movies_star.mvp.presenter.DetailPresenterImp;
import com.example.dimas.popular_movies_star.mvp.view.DetailMovieView;
import com.example.dimas.popular_movies_star.ui.activity.MainActivity;
import com.example.dimas.popular_movies_star.ui.adapter.TrailerAdapter;
import com.example.dimas.popular_movies_star.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_IMAGE_MOVIE_TRANSITION_NAME;

/**
 * Created by       : dimas on 02/02/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailMovieFragment extends Fragment implements DetailMovieView{

    AppCompatActivity activity;

    ImageView imageBackdrop;
    ImageView imagePoster;
    TextView titleMovie;
    TextView ratingMovie;
    TextView releaseDate;
    TextView overviewMovie;

    Movie movie;
    Bundle bundle;

    DetailPresenter detailPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = this.getArguments();

        if (bundle != null){
            movie = bundle.getParcelable(MainActivity.EXTRA_MOVIE);
        }

        activity = ((AppCompatActivity) getActivity());
        detailPresenter = new DetailPresenterImp(activity, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_movie, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        showDetailMovie();
    }

    private void initViews(View view){

        Toolbar toolbar = view.findViewById(R.id.toolbar_detail);
        activity.setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view1 -> activity.finish());


        if (activity.getSupportActionBar() != null){
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        CollapsingToolbarLayout collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, android.R.color.white));

        imageBackdrop = view.findViewById(R.id.backdrop_image);
        imagePoster = view.findViewById(R.id.poster_image);

        titleMovie = view.findViewById(R.id.title_movie);
        ratingMovie = view.findViewById(R.id.rating_movie);
        releaseDate = view.findViewById(R.id.release_date);
        overviewMovie = view.findViewById(R.id.overview_text);
    }

    @Override
    public void showDetailMovie() {
        if (movie != null) {
            showLoading(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String imageMovieTransitionName = bundle.getString(EXTRA_IMAGE_MOVIE_TRANSITION_NAME);
                imagePoster.setTransitionName(imageMovieTransitionName);
            }
            Picasso.with(activity)
                    .load(movie.getBackdrop_path())
                    .into(imageBackdrop, new Callback() {
                        @Override
                        public void onSuccess() {
                            activity.supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            activity.supportStartPostponedEnterTransition();
                        }
                    });
            Picasso.with(activity).load(movie.getPoster_path()).into(imagePoster);
            titleMovie.setText(movie.getTitle());
            String release = getResources().getString(R.string.release_date) + movie.getRelease_date();
            ratingMovie.setText(String.valueOf(movie.getVote_average()));
            releaseDate.setText(release);
            overviewMovie.setText(movie.getOverview());

            detailPresenter.setupTrailerVideo(movie.getId());

        } else {
            showError("Movie gagal di load");
        }
    }

    @Override
    public void showTrailer(List<Trailer> listT) {
        RecyclerView recyclerView = activity.findViewById(R.id.recyclerView_trailer);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        TrailerAdapter trailerAdapter = new TrailerAdapter(activity, listT);
        trailerAdapter.setOnTrailerClickListener(position -> {
            Trailer trailer = trailerAdapter.getItem(position);
            if (trailer != null){
                Intent intent = null;
                try {
                    intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(Constants.APIConstants.BASE_YOUTUBE_URL + trailer.getKey()));
                } catch (ActivityNotFoundException ex){
                    showError(ex.getMessage());
                }
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(trailerAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void showLoading(boolean b) {

    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG).show();
    }

}

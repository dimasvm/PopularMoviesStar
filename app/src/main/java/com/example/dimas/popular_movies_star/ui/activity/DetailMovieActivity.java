package com.example.dimas.popular_movies_star.ui.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.data.model.Trailer;
import com.example.dimas.popular_movies_star.mvp.presenter.DetailPresenter;
import com.example.dimas.popular_movies_star.mvp.presenter.DetailPresenterImp;
import com.example.dimas.popular_movies_star.mvp.view.DetailMovieView;
import com.example.dimas.popular_movies_star.ui.adapter.TrailerAdapter;
import com.example.dimas.popular_movies_star.utils.Constants;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_IMAGE_MOVIE_TRANSITION_NAME;
import static com.example.dimas.popular_movies_star.ui.activity.MainActivity.EXTRA_MOVIE;

/**
 * Created by       : dimas on 29/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class DetailMovieActivity
        extends AppCompatActivity implements DetailMovieView {

    private ImageView imageBackdrop;
    private ImageView imagePoster;
    private TextView titleMovie;
    private TextView ratingMovie;
    private TextView releaseDate;
    private TextView overviewMovie;

    private Movie movie;
    Bundle bundle;
    DetailPresenter detailPresenter;
    List<Trailer> trailerList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        supportPostponeEnterTransition();

        bundle = getIntent().getExtras();

        if (bundle != null) {
            movie = bundle.getParcelable(EXTRA_MOVIE);
        }

        detailPresenter = new DetailPresenterImp(this, this);

        initViews();

        showDetailMovie();

    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, android.R.color.white));

        imageBackdrop = findViewById(R.id.backdrop_image);
        imagePoster = findViewById(R.id.poster_image);

        titleMovie = findViewById(R.id.title_movie);
        ratingMovie = findViewById(R.id.rating_movie);
        releaseDate = findViewById(R.id.release_date);
        overviewMovie = findViewById(R.id.overview_text);

    }

    @Override
    public void showDetailMovie() {
        if (movie != null) {
            showLoading(false);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String imageMovieTransitionName = bundle.getString(EXTRA_IMAGE_MOVIE_TRANSITION_NAME);
                imagePoster.setTransitionName(imageMovieTransitionName);
            }
            Picasso.with(this)
                    .load(movie.getBackdrop_path())
                    .into(imageBackdrop, new Callback() {
                        @Override
                        public void onSuccess() {
                            supportStartPostponedEnterTransition();
                        }

                        @Override
                        public void onError() {
                            supportStartPostponedEnterTransition();
                        }
                    });
            Picasso.with(this).load(movie.getPoster_path()).into(imagePoster);
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
        RecyclerView recyclerView = findViewById(R.id.recyclerView_trailer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // TODO : Set Adapter recyclerview trailer
        TrailerAdapter trailerAdapter = new TrailerAdapter(this, listT);
        // todo set listener trailer adapter
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
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        supportFinishAfterTransition();
    }
}

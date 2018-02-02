package com.example.dimas.popular_movies_star.ui.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.mvp.presenter.MainPresenter;
import com.example.dimas.popular_movies_star.mvp.presenter.MainPresenterImp;
import com.example.dimas.popular_movies_star.mvp.view.MainView;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;
import com.example.dimas.popular_movies_star.ui.adapter.MovieClickListener;

import java.util.List;

import static com.example.dimas.popular_movies_star.OptionMovie.POPULAR_MOVIE;
import static com.example.dimas.popular_movies_star.OptionMovie.UPCOMING_MOVIE;

public class MainActivity extends AppCompatActivity implements MainView, MovieClickListener {

    private static final String TAG = "MainActivity";
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_IMAGE_MOVIE_TRANSITION_NAME = "movieimage";
    int currentPage = 1;
    // Presenter
    MainPresenter mainPresenter;
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;

    // For keep position state recyclerview
    public static int index = -1;
    public static int top = -1;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        mainPresenter = new MainPresenterImp(this, this);
        mainPresenter.getMovies(currentPage, POPULAR_MOVIE);

    }

    private void initViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        // set juga lastvisible position
//        int gridColumn = getResources().getInteger(R.integer.grid_column_count);
//        linearLayoutManager = new GridLayoutManager(this, gridColumn);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }


    @Override
    public void showLoading(boolean b) {
        if (b) {
            progressBar.setVisibility(View.VISIBLE);
        } else progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, "Error :\n " + errorMessage, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showMovie(List<Movie> movies) {
        movieAdapter = new MovieAdapter(this, movies, this);
        mRecyclerView.setAdapter(movieAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenu = item.getItemId();

        switch (selectedMenu) {
            case R.id.popular:
                mainPresenter.getMovies(currentPage, POPULAR_MOVIE);
                item.setChecked(true);
                break;
            case R.id.upcoming:
                mainPresenter.getMovies(currentPage, UPCOMING_MOVIE);
                item.setChecked(true);
                break;
            case R.id.now_playing:
                mainPresenter.getNowPlaying(currentPage);
                item.setChecked(true);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClicked(Movie movie, ImageView sharedImage) {
        Intent intent = new Intent(this, DetailMovieActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        intent.putExtra(EXTRA_IMAGE_MOVIE_TRANSITION_NAME, ViewCompat.getTransitionName(sharedImage));
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                sharedImage,
                ViewCompat.getTransitionName(sharedImage));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options.toBundle());
        } else startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        index = linearLayoutManager.findFirstVisibleItemPosition();
        View v = mRecyclerView.getChildAt(0);
        top = (v == null) ? 0 : (v.getTop() - mRecyclerView.getPaddingTop());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (index != -1) {
            linearLayoutManager.scrollToPositionWithOffset(index, top);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

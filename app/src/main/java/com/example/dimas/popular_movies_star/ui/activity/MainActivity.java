package com.example.dimas.popular_movies_star.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimas.popular_movies_star.OptionMovie;
import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.mvp.presenter.MainPresenter;
import com.example.dimas.popular_movies_star.mvp.presenter.MainPresenterImp;
import com.example.dimas.popular_movies_star.mvp.view.MainView;
import com.example.dimas.popular_movies_star.ui.adapter.MovieAdapter;
import com.example.dimas.popular_movies_star.ui.adapter.MovieClickListener;
import com.example.dimas.popular_movies_star.utils.EndlessRecyclerViewScrollListener;

import java.util.List;

import static com.example.dimas.popular_movies_star.OptionMovie.NOW_PLAYING;
import static com.example.dimas.popular_movies_star.OptionMovie.POPULAR_MOVIE;

public class MainActivity extends AppCompatActivity implements MainView, MovieClickListener {

    // Constants
    private static final String TAG = "MainActivity";
    public static final String EXTRA_MOVIE = "movie";
    public static final String EXTRA_IMAGE_MOVIE_TRANSITION_NAME = "movieimage";
    int currentPage = 1;

    // Variabel Utils
    private boolean isOnline;
    private boolean isTwoPane;

    // Views
    private RecyclerView mRecyclerView;
    private MovieAdapter movieAdapter;
    private ProgressBar progressBar;
    private TextView errorTextView;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    // Presenter
    MainPresenter mainPresenter;

    // For keep position state recyclerview
    public static int index = -1;
    public static int top = -1;

    // CURRENT OPTION
    OptionMovie CURRENT_OPTION = POPULAR_MOVIE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: --");

        initViews();
        mainPresenter = new MainPresenterImp(this, this, mRecyclerView);
        mainPresenter.getPopularMovies(currentPage);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                int offset = page + 1;
                Log.d(TAG, "onLoadMore: Load page ke - " + offset + ", dengan total item " + totalItemsCount);
                if (CURRENT_OPTION == NOW_PLAYING){
                    mainPresenter.getNowPlaying(offset);
                } else mainPresenter.getPopularMovies(offset);

            }
        });

    }

    private void initViews() {
        Log.d(TAG, "initViews: Menginisialisasi view MainActivity");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

        mRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.errorTextView);
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(mRecyclerView);
        // set juga lastvisible position
        int gridColumn = getResources().getInteger(R.integer.grid_column_count);
        gridLayoutManager = new GridLayoutManager(this, gridColumn);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);


        movieAdapter = new MovieAdapter(this, this);
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

        if (movieAdapter!=null){
            movieAdapter.addAll(movies);
            if (movieAdapter.getItemCount() <= movies.size()){
                mRecyclerView.setAdapter(movieAdapter);
            }
            Log.d(TAG, "showMovie: Menampilkan " + movieAdapter.getItemCount() + " movies ke recycler view");
        }


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
                mainPresenter.getPopularMovies(currentPage);
                item.setChecked(true);
                CURRENT_OPTION = POPULAR_MOVIE;
                break;
            case R.id.now_playing:
                mainPresenter.getNowPlaying(currentPage);
                item.setChecked(true);
                CURRENT_OPTION = NOW_PLAYING;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClicked(Movie movie, ImageView sharedImage) {
        mainPresenter.getDetailPage(movie, sharedImage);
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

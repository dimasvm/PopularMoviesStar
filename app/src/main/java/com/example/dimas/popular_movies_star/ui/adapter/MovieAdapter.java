package com.example.dimas.popular_movies_star.ui.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dimas.popular_movies_star.OnLoadMoreListener;
import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.example.dimas.popular_movies_star.utils.EndlessRecyclerViewScrollListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.dimas.popular_movies_star.OptionMovie.POPULAR_MOVIE;

/**
 * Created by       : dimas on 12/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MovieAdapter
        extends RecyclerView.Adapter {

    private Context mContext;
    private List<Movie> mMoviesList;
    private MovieClickListener movieClickListener;

    private int VIEW_TYPE_ITEM = 0;
    private int VIEW_TYPE_LOADING = 1;

    private final String TAG = MovieAdapter.class.getSimpleName();

    public MovieAdapter(Context context, MovieClickListener movieClickListener) {

        mMoviesList = new ArrayList<>();
        mContext = context;
        this.movieClickListener = movieClickListener;
    }


    public void addAll(List<Movie> movies) {
        int curSize = getItemCount();
        mMoviesList.addAll(movies);
        notifyItemRangeInserted(curSize, mMoviesList.size() - 1);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder vh;
        View view;
        if (viewType == VIEW_TYPE_ITEM){
            view = inflater.inflate(R.layout.item_grid_movie, parent, false);
            return new MovieViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_load_more, parent, false);
            return new ProgressBarViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MovieViewHolder){
            Movie movie = mMoviesList.get(position);
            ((MovieViewHolder) holder).bindData(movie);
        } else {
            ((ProgressBarViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mMoviesList.get(position) != null ? VIEW_TYPE_ITEM : VIEW_TYPE_LOADING;
    }

    @Override
    public int getItemCount() {
        return mMoviesList.size();
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageMovie;
        TextView titleMovie;
        TextView ratingMovie;


        MovieViewHolder(View itemView) {
            super(itemView);

            imageMovie = itemView.findViewById(R.id.imageMovie);
            titleMovie = itemView.findViewById(R.id.titleMovie);
            ratingMovie = itemView.findViewById(R.id.ratingMovie);

            itemView.setOnClickListener(this);

        }

        void bindData(Movie movie) {
            Picasso.with(itemView.getContext())
                    .load(movie.getPoster_path())
                    .placeholder(android.R.drawable.stat_sys_warning)
                    .error(android.R.drawable.stat_notify_error)
                    .into(imageMovie);
            titleMovie.setText(movie.getTitle());
            ratingMovie.setText(String.valueOf(movie.getVote_average()));

            ViewCompat.setTransitionName(imageMovie, movie.getTitle());
        }

        @Override
        public void onClick(View view) {
            movieClickListener.onMovieClicked(mMoviesList.get(getAdapterPosition()), imageMovie);
        }
    }

    private class ProgressBarViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        ProgressBarViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBarOnLoadMore);
        }
    }
}

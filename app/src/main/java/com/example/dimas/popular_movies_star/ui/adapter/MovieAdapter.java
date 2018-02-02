package com.example.dimas.popular_movies_star.ui.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimas.popular_movies_star.R;
import com.example.dimas.popular_movies_star.data.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by       : dimas on 12/01/18.
 * Email            : araymaulana66@gmail.com
 */

public class MovieAdapter
        extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMoviesList;
    private MovieClickListener movieClickListener;


    public MovieAdapter(Context context, List<Movie> movies, MovieClickListener movieClickListener) {
        mMoviesList = new ArrayList<>();
        addAll(movies);
        mContext = context;
        this.movieClickListener = movieClickListener;
    }

    public void addAll(List<Movie> movies) {
        mMoviesList.clear();
        mMoviesList.addAll(movies);
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_grid_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        holder.bindData(movie);
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
            Picasso.with(mContext)
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

}

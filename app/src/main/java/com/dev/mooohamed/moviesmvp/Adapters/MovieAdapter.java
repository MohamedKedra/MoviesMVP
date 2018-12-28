package com.dev.mooohamed.moviesmvp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;
import com.dev.mooohamed.moviesmvp.R;
import com.dev.mooohamed.moviesmvp.Services.Urls;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieHolder> {

    Context context;
    List<Movie> movies;
    OnItemClickListener onItemClickListener;

    public MovieAdapter(Context context, List<Movie> movies){

        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MovieHolder(LayoutInflater.from(context).inflate(R.layout.movie_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        Picasso.with(context).load(Urls.ImageBase+movies.get(i).getPoster()).into(movieHolder.poster);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster)
        ImageView poster;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null && getAdapterPosition() != RecyclerView.NO_POSITION)
                        onItemClickListener.onClick(movies.get(getAdapterPosition()));
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onClick(Movie movie);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}

package com.dev.mooohamed.moviesmvp.UI.Details;

import android.content.Context;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

public class DetailsPresenter implements DetailsContract.FavoritePresenter{

    DetailsData detailsData;
    DetailsContract.FavoriteView favoriteView;

    public DetailsPresenter(Context context, DetailsContract.FavoriteView favoriteView){
        detailsData = new DetailsData(context);
        this.favoriteView = favoriteView;
    }

    @Override
    public void addMovie(Movie movie) {
        detailsData.addNewMovie(movie);
    }

    @Override
    public void removeFavorite(Movie movie) {
        detailsData.deleteMovie(movie);
    }

    @Override
    public void isFavorite(boolean state) {
        favoriteView.isFavorite(state);
    }

    @Override
    public void findMovie(int movieId) {
        detailsData.findMovie(movieId,favoriteView);
    }
}
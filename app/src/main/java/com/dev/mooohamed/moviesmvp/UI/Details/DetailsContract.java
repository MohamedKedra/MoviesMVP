package com.dev.mooohamed.moviesmvp.UI.Details;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

public interface DetailsContract {

    interface FavoriteView{

        void isFavorite(boolean state);
    }

    interface FavoritePresenter{

        void addMovie(Movie movie);
        void removeFavorite(Movie movie);
        void isFavorite(boolean state);
        void findMovie(int movieId);
    }

    interface FavoriteData {

        void findMovie(int movieId);
    }
}

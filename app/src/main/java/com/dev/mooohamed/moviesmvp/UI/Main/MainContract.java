package com.dev.mooohamed.moviesmvp.UI.Main;

import android.content.Context;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

import java.util.List;

public interface MainContract {

    interface ReceiveMoviesViewListener{

        void OnReceive(List<Movie> movies);
    }

    interface ReceiveTypeDataListener{

        void OnReceive(String type, MainContract.ReceiveMoviesViewListener viewListener, Context context);
    }


    interface SendDataPresenterListener{

        void OnSendType(String type);
        void OnSendMovies(List<Movie> movies);
    }
}

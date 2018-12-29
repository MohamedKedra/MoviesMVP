package com.dev.mooohamed.moviesmvp.UI.Main;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

import java.util.List;

public class MainPresenter implements MainContract.SendDataPresenterListener {

    MainContract.ReceiveMoviesViewListener viewListener;
    MainData mainData;

    public MainPresenter(MainContract.ReceiveMoviesViewListener viewListener){
        this.viewListener = viewListener;

    }

    @Override
    public void OnSendType(String type) {
        mainData = new MainData();
        mainData.OnReceive(type,viewListener);
    }

    @Override
    public void OnSendMovies(List<Movie> movies) {
        viewListener.OnReceive(movies);
    }
}
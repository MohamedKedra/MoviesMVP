package com.dev.mooohamed.moviesmvp.UI.Main;

import android.content.Context;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;

import java.util.List;

public class MainPresenter implements MainContract.SendDataPresenterListener {

    MainContract.ReceiveMoviesViewListener viewListener;
    MainData mainData;
    Context context;

    public MainPresenter(MainContract.ReceiveMoviesViewListener viewListener, Context context){
        this.viewListener = viewListener;
        this.context = context;
    }

    @Override
    public void OnSendType(String type) {
        mainData = new MainData();
        mainData.OnReceive(type,viewListener,context);
    }

    @Override
    public void OnSendMovies(List<Movie> movies) {
        viewListener.OnReceive(movies);
    }
}
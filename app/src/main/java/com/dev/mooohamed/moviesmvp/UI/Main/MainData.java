package com.dev.mooohamed.moviesmvp.UI.Main;

import android.content.Context;
import android.widget.Toast;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;
import com.dev.mooohamed.moviesmvp.Data.Models.MovieResponse;
import com.dev.mooohamed.moviesmvp.Services.API;
import com.dev.mooohamed.moviesmvp.Services.Urls;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainData implements MainContract.ReceiveTypeDataListener {

    Retrofit retrofit;
    API api;
    Observable<MovieResponse> call;
    MainContract.SendDataPresenterListener presenterListener;
    List<Movie> movies;
    Context context;

    @Override
    public void OnReceive(String type, MainContract.ReceiveMoviesViewListener viewListener, Context context) {

        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.base)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        api = retrofit.create(API.class);
        if(type.equals(Urls.Popular)){
            call = api.getPopularMovies(Urls.key);
        }else {
            call = api.getRatedMovies(Urls.key);
        }
        call.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
        presenterListener = new MainPresenter(viewListener,context);
    }

    Observer<MovieResponse> observer = new Observer<MovieResponse>() {
        @Override
        public void onSubscribe(Disposable d) {
        }

        @Override
        public void onNext(MovieResponse movieResponse) {
            movies = new ArrayList<>(movieResponse.getResults());
        }

        @Override
        public void onError(Throwable e) {
            Toast.makeText(context,"Problem is found",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete() {
            presenterListener.OnSendMovies(movies);
        }
    };
/*
    Callback<MovieResponse> callback = new Callback<MovieResponse>() {
        @Override
        public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
            if (response.isSuccessful()) {
                presenterListener.OnSendMovies(response.body().getResults());
            }else {
                System.out.println(response.message());
            }
        }

        @Override
        public void onFailure(Call<MovieResponse> call, Throwable t) {
            System.out.println(t.getMessage());
        }
    };*/
}

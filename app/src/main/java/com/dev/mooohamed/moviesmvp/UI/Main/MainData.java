package com.dev.mooohamed.moviesmvp.UI.Main;

import com.dev.mooohamed.moviesmvp.Data.Models.MovieResponse;
import com.dev.mooohamed.moviesmvp.Services.API;
import com.dev.mooohamed.moviesmvp.Services.Urls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainData implements MainContract.ReceiveTypeDataListener {

    Retrofit retrofit;
    API api;
    Call<MovieResponse> call;
    MainContract.SendDataPresenterListener presenterListener;

    @Override
    public void OnReceive(String type,MainContract.ReceiveMoviesViewListener viewListener) {

        retrofit = new Retrofit.Builder()
                .baseUrl(Urls.base)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(API.class);
        if(type.equals(Urls.Popular)){
            call = api.getPopularMovies(Urls.key);
        }else {
            call = api.getRatedMovies(Urls.key);
        }
        call.enqueue(callback);
        presenterListener = new MainPresenter(viewListener);
    }

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
    };
}

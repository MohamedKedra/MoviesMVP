package com.dev.mooohamed.moviesmvp.UI.Main;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.dev.mooohamed.moviesmvp.UI.MovieAdapter;
import com.dev.mooohamed.moviesmvp.Data.Models.Movie;
import com.dev.mooohamed.moviesmvp.R;
import com.dev.mooohamed.moviesmvp.Services.Urls;
import com.dev.mooohamed.moviesmvp.UI.Details.DetailsActivity;
import com.dev.mooohamed.moviesmvp.UI.Details.DetailsData;
import com.dev.mooohamed.moviesmvp.UI.Login.LoginActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.ReceiveMoviesViewListener {

    @BindView(R.id.rv_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    GridLayoutManager layoutManager;
    MovieAdapter adapter;
    MainContract.SendDataPresenterListener presenterListener;
    public static final String MovieKey = "movie";
    final static String StateKey = "state";
    final static String PosKey = "pos";
    final static String IndexKey = "index";
    Parcelable stateData;
    int index = 0;
    DetailsData detailsData;
    Display display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenterListener = new MainPresenter(this,this);
        detailsData = new DetailsData(this); // for access data from database
        display = getWindowManager().getDefaultDisplay();
        checkMode();
        // to handle mobile rotation and recyclerview display the same index of items
        if (savedInstanceState != null) {

            System.out.println("index : "+index);
            if (savedInstanceState.containsKey(IndexKey)) {
                int i = savedInstanceState.getInt(IndexKey);
                if (i == 0) {
                    getSupportActionBar().setTitle(R.string.popular);
                    presenterListener.OnSendType(Urls.Popular);
                    index = 0;
                } else if (i == 1) {
                    getSupportActionBar().setTitle(R.string.rated);
                    presenterListener.OnSendType(Urls.Rated);
                    index = 1;
                } else {
                    showFavorite();
                    index = 2;
                }
            }
            if (savedInstanceState.containsKey(PosKey)){
                recyclerView.scrollToPosition(savedInstanceState.getInt(PosKey));
            }
        }else {
            getSupportActionBar().setTitle(R.string.popular);
            presenterListener.OnSendType(Urls.Popular);
        }
    }

    @SuppressLint("WrongConstant")
    public void checkMode(){
        display = getWindowManager().getDefaultDisplay();
        if (display.getRotation() == 0){
            layoutManager = new GridLayoutManager(this, 2);
        }else {
            layoutManager = new GridLayoutManager(this, 3);
        }
    }

    public void showFavorite(){
        detailsData.getAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter = new MovieAdapter(MainActivity.this,movies);
                adapter.setOnItemClickListener(onItemClickListener);
                recyclerView.setAdapter(adapter);
            }
        });
        getSupportActionBar().setTitle(R.string.favorite);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        stateData = layoutManager.onSaveInstanceState();
        outState.putParcelable(StateKey,stateData);
        int pos = layoutManager.findFirstCompletelyVisibleItemPosition();
        if (pos == -1){
            pos = layoutManager.findLastVisibleItemPosition();
        }
        outState.putInt(PosKey,pos);
        outState.putInt(IndexKey,index);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null){
            stateData = savedInstanceState.getParcelable(StateKey);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stateData != null){
            layoutManager.onRestoreInstanceState(stateData);
        }
    }

    @Override
    public void OnReceive(List<Movie> movies) {
        progressBar.setVisibility(View.GONE);
        adapter = new MovieAdapter(MainActivity.this, movies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(onItemClickListener);
    }

    MovieAdapter.OnItemClickListener onItemClickListener = new MovieAdapter.OnItemClickListener() {
        @Override
        public void onClick(Movie movie) {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
            intent.putExtra(MovieKey , movie);
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.mi_popular:
                presenterListener.OnSendType(Urls.Popular);
                getSupportActionBar().setTitle(R.string.popular);
                index = 0;
                return true;

            case R.id.mi_rated:
                presenterListener.OnSendType(Urls.Rated);
                getSupportActionBar().setTitle(R.string.rated);
                index = 1;
                return true;

            case R.id.mi_favorite:
                showFavorite();
                index = 2;
                return true;

            case R.id.mi_profile:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
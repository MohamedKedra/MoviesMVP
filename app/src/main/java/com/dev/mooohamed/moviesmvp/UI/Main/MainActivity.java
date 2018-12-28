package com.dev.mooohamed.moviesmvp.UI.Main;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.dev.mooohamed.moviesmvp.Adapters.MovieAdapter;
import com.dev.mooohamed.moviesmvp.Data.DB.MovieDatabase;
import com.dev.mooohamed.moviesmvp.Data.Models.Movie;
import com.dev.mooohamed.moviesmvp.R;
import com.dev.mooohamed.moviesmvp.Services.Urls;
import com.dev.mooohamed.moviesmvp.UI.Details.DetailsActivity;
import com.dev.mooohamed.moviesmvp.UI.Details.DetailsData;
import com.dev.mooohamed.moviesmvp.UI.Details.DetailsPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainContract.ReceiveMoviesViewListener {

    @BindView(R.id.rv_recycler)
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    MovieAdapter adapter;
    MainContract.SendDataPresenterListener presenterListener;
    public static final String MovieKey = "movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenterListener = new MainPresenter(this);
        presenterListener.OnSendType(Urls.Popular);
        layoutManager = new GridLayoutManager(this, 2);
        getSupportActionBar().setTitle(Urls.Popular);
    }

    @Override
    public void OnReceive(List<Movie> movies) {
        adapter = new MovieAdapter(MainActivity.this, movies);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onClick(Movie movie) {
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra(MovieKey , movie);
                startActivity(intent);
            }
        });
    }

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
                getSupportActionBar().setTitle(Urls.Popular);
                return true;

            case R.id.mi_rated:
                presenterListener.OnSendType(Urls.Rated);
                getSupportActionBar().setTitle(Urls.Rated);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
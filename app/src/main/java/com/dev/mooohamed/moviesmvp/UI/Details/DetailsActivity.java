package com.dev.mooohamed.moviesmvp.UI.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.mooohamed.moviesmvp.Data.Models.Movie;
import com.dev.mooohamed.moviesmvp.R;
import com.dev.mooohamed.moviesmvp.Services.Urls;
import com.dev.mooohamed.moviesmvp.UI.Main.MainActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity implements DetailsContract.FavoriteView {

    @BindView(R.id.iv_poster)
    ImageView poster;
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.tv_date)
    TextView date;
    @BindView(R.id.tv_rate)
    TextView rate;
    @BindView(R.id.tv_overview)
    TextView overview;
    @BindView(R.id.btn_favorite)
    Button favorite;
    Movie movie;
    DetailsContract.FavoritePresenter presenter;
    static boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(MainActivity.MovieKey)){
            movie = intent.getExtras().getParcelable(MainActivity.MovieKey);
            showDetails(movie);
        }
        presenter = new DetailsPresenter(DetailsActivity.this,this);
        presenter.findMovie(movie.getId());
        favorite.setOnClickListener(listener);
    }

    public void showDetails(Movie movie) {
        getSupportActionBar().setTitle(movie.getTitle());
        title.setText(movie.getTitle());
        Picasso.with(this).load(Urls.ImageBase+movie.getPoster()).into(poster);
        date.setText(movie.getDate());
        rate.setText(movie.getRate());
        overview.setText(movie.getOverview());
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isFavorite){
                presenter.removeFavorite(movie);
                Toast.makeText(DetailsActivity.this, movie.getTitle()+getResources().getString(R.string.remove_fav), Toast.LENGTH_SHORT).show();
                favorite.setBackgroundResource(R.drawable.add_favorite_design);
            }else {
                presenter.addMovie(movie);
                Toast.makeText(DetailsActivity.this, movie.getTitle()+getResources().getString(R.string.add_fav), Toast.LENGTH_SHORT).show();
                favorite.setBackgroundResource(R.drawable.remove_favorite_design);

            }
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void isFavorite(boolean state) {
        isFavorite = state;
        if (isFavorite){
            favorite.setBackgroundResource(R.drawable.remove_favorite_design);
        }else {
            favorite.setBackgroundResource(R.drawable.add_favorite_design);
        }
    }
}
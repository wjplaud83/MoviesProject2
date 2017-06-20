package com.applaudstudios.android.moviesproject2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.applaudstudios.android.moviesproject2.model.movieGeneralModel;

import static com.applaudstudios.android.moviesproject2.R.id.movie_detail_container;

public class movieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Intent intent = getIntent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieGeneralModel moviegeneralModel = (movieGeneralModel) intent.getSerializableExtra("DATA_MOVIE");

        if (savedInstanceState == null) {

            movieDetailFragment fragment = new movieDetailFragment();
            fragment.setMovieData(moviegeneralModel);
            getSupportFragmentManager().beginTransaction()
                    .add(movie_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() { super.onBackPressed();}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.applaudstudios.android.moviesproject2.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applaudstudios.android.moviesproject2.R;
import com.applaudstudios.android.moviesproject2.model.movieGeneralModel;
import com.applaudstudios.android.moviesproject2.movieDetailActivity;
import com.applaudstudios.android.moviesproject2.movieDetailFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by wjplaud83 on 6/19/17.
 */

public class movieGeneralAdapter extends RecyclerView.Adapter<movieGeneralHolder> {
    private List<movieGeneralModel> mMovieGeneralModel;
    private Context context;
    private boolean mTwoPane;
    private FragmentManager fm;

    public movieGeneralAdapter(Context context, List<movieGeneralModel> itemList, boolean mTwoPane, FragmentManager fm) {
        this.mMovieGeneralModel = itemList;
        this.context = context;
        this.mTwoPane = mTwoPane;
        this.fm = fm;
    }

    @Override
    public movieGeneralHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_cards, null);
        movieGeneralHolder rcv = new movieGeneralHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(movieGeneralHolder holder, final int position) {
        holder.movieName.setText(mMovieGeneralModel.get(position).getTitle());
        holder.movieAvg.setText(mMovieGeneralModel.get(position).getmVote());
        //picasso loading here
        Picasso.with(context)
                .load(mMovieGeneralModel.get(position).getThumbnail())
                .into(holder.moviePhoto);
        if (position == 0 && mTwoPane) {
            movieDetailFragment fragment = new movieDetailFragment();
            fragment.setMovieData(mMovieGeneralModel.get(0));
            fragment.setArgument(fm);
            fm
                    .beginTransaction()
                    .replace(R.id.movie_detail_container, fragment)
                    .commit();
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTwoPane) {
                    movieDetailFragment fragment = new movieDetailFragment();
                    fragment.setMovieData(mMovieGeneralModel.get(position));
                    fragment.setArgument(fm);
                    fm
                            .beginTransaction()
                            .replace(R.id.movie_detail_container, fragment)
                            .commit();
                } else {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, movieDetailActivity.class);
                    intent.putExtra("DATA_MOVIE", mMovieGeneralModel.get(position));
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mMovieGeneralModel.size();
    }
}



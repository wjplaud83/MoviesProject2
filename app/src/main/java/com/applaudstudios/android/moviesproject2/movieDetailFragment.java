package com.applaudstudios.android.moviesproject2;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applaudstudios.android.moviesproject2.database.favoriteSqliteHelper;
import com.applaudstudios.android.moviesproject2.model.movieGeneralModel;
import com.applaudstudios.android.moviesproject2.model.trailer.movieYoutubeModel;
import com.applaudstudios.android.moviesproject2.constants.constant;
import com.applaudstudios.android.moviesproject2.model.review.Results;
import com.applaudstudios.android.moviesproject2.model.review.movieReview;
import com.applaudstudios.android.moviesproject2.network.MovieAPI;
import com.applaudstudios.android.moviesproject2.network.NetworkAPI;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by wjplaud83 on 6/19/17.
 */

public class movieDetailFragment extends Fragment {

    private FragmentManager fm;
    private movieGeneralModel moviegeneralModel;
    private TextView reviewText, titleText, voteText, peopleText, calendarText, plotSynopsis;
    private ImageView titleImage;
    private LinearLayout youtubeViewHolder;
    private TextView shareYoutube;
    private String shareYoutubeID;
    private FloatingActionButton fab;

    public movieDetailFragment() {

    }

    public void setArgument(FragmentManager fm) {
        this.fm = fm;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_detail, container, false);
        if (savedInstanceState != null) {
            this.moviegeneralModel = (movieGeneralModel) savedInstanceState.getSerializable("DATA");
        }
        updateGeneralUI(rootView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("DATA", moviegeneralModel);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void setMovieData(movieGeneralModel moviegeneralModel) {
        this.moviegeneralModel = moviegeneralModel;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void updateGeneralUI(View v) {
        titleText = (TextView) v.findViewById(R.id.titleText);
        voteText = (TextView) v.findViewById(R.id.rating);
        calendarText = (TextView) v.findViewById(R.id.calendar);
        peopleText = (TextView) v.findViewById(R.id.people);
        titleImage = (ImageView) v.findViewById(R.id.titleimg);
        plotSynopsis = (TextView) v.findViewById(R.id.plotsynopsis);
        reviewText = (TextView) v.findViewById(R.id.reviewText);
        youtubeViewHolder = (LinearLayout) v.findViewById(R.id.youtubelayout);
        shareYoutube = (TextView) v.findViewById(R.id.youtubesharer);
        fab = (FloatingActionButton) v.findViewById(R.id.fab);

        titleText.setText(moviegeneralModel.getTitle());
        voteText.setText(moviegeneralModel.getmVote());
        peopleText.setText(moviegeneralModel.getmPeople());
        calendarText.setText(moviegeneralModel.getmReleaseDate());
        plotSynopsis.setText(moviegeneralModel.getmOverview());
        getMovieReview(reviewText);
        Picasso.with(getContext())
                .load(moviegeneralModel.getThumbnail())
                .into(titleImage);
        getMovieReview(reviewText);
        getTrailer(youtubeViewHolder);
        shareYoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shareYoutubeID != null) {
                    shareYoutubeIntent(shareYoutubeID);
                } else {
                    Toast.makeText(getContext(), "No Youtube Videos Available! Sorry", Toast.LENGTH_LONG).show();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDatabase();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void saveToDatabase() {
        favoriteSqliteHelper db = new favoriteSqliteHelper(getContext());
        if (!reviewText.getText().toString().contains("Sorry")) {
            moviegeneralModel.setmReview(reviewText.getText().toString());
        }
        boolean b = db.insertMovie(moviegeneralModel);
        if (b)
            Toast.makeText(getContext(), "Added to Favourites", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(getContext(), "Seems Already in Favourites!", Toast.LENGTH_LONG).show();
    }

    protected void shareYoutubeIntent(String shareYoutubeID) {
        String url = "http://www.youtube.com/watch?v" + shareYoutubeID;
        String shareMsg = "hey,there is a new film named " + moviegeneralModel.getTitle() + " has been released and here is the Trailer link. Take a look at it at: " + url;
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Movies Project2 - Android App");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareMsg);
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
    }

    protected String generateYoutubeThumbnailURL(String id) {
        String url = "http://img.youtube.com/vi/" + id + "/mqdefault.jpg";
        return url;
    }

    public void watchYoutubeVideo(String id) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }
    }

    protected void getTrailer(final LinearLayout youtubeViewHolder) {
        MovieAPI mMovieAPI = NetworkAPI.createService(MovieAPI.class);
        mMovieAPI.fetchVideos(constant.API_KEY, this.moviegeneralModel.getmId(), new Callback<movieYoutubeModel>() {

            @Override
            public void success(movieYoutubeModel movieYoutubeModel, Response response) {
                youtubeViewHolder.setPadding(5, 10, 5, 0);
                com.applaudstudios.android.moviesproject2.model.trailer.Results[] trailer = movieYoutubeModel.getResults();
                if (trailer.length > 0) {
                    shareYoutubeID = trailer[0].getKey();
                    for (final com.applaudstudios.android.moviesproject2.model.trailer.Results obj : trailer) {
                        String url = generateYoutubeThumbnailURL(obj.getKey());
                        ImageView myImage = new ImageView(getContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                180,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        params.leftMargin = 3;
                        params.rightMargin = 3;
                        params.topMargin = 6;
                        params.bottomMargin = 3;
                        myImage.setLayoutParams(params);
                        Picasso.with(getContext())
                                .load(url)
                                .into(myImage);
                        youtubeViewHolder.addView(myImage);
                        myImage.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                watchYoutubeVideo(obj.getKey());
                            }
                        });

                    }

                } else {
                    youtubeViewHolder.setPadding(50, 50, 50, 50);
                    TextView errmsg = new TextView(getContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            30
                    );
                    errmsg.setLayoutParams(params);
                    errmsg.setText("That's Bad Luck,No Trailers Found!Check later");
                    youtubeViewHolder.addView(errmsg);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                youtubeViewHolder.setPadding(50, 50, 50, 50);
                TextView errmsg = new TextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        30
                );
                errmsg.setLayoutParams(params);
                errmsg.setText("Network Error! You can't view Trailers Rite Now");
                youtubeViewHolder.addView(errmsg);

            }
        });
    }

    protected void getMovieReview(final View review) {
        MovieAPI mMovieAPI = NetworkAPI.createService(MovieAPI.class);
        mMovieAPI.fetchReview(constant.API_KEY, this.moviegeneralModel.getmId(), new Callback<movieReview>() {

            @Override
            public void success(movieReview movieReview, Response response) {
                Results[] movieResult = movieReview.getResults();
                if (movieResult.length > 0)
                    ((TextView) review).setText(movieResult[0].getContent());
                else
                    ((TextView) review).setText("Sorry No Review is Available Till Now!");

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("error", error.toString());
                ((TextView) review).setText("Sorry! Check Back Latter! Network Error!");
            }
        });
    }

    protected void generateThumbnail() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
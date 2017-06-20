package com.applaudstudios.android.moviesproject2.network;

import com.applaudstudios.android.moviesproject2.model.movieGeneral;
import com.applaudstudios.android.moviesproject2.model.review.movieReview;
import com.applaudstudios.android.moviesproject2.model.trailer.movieYoutubeModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by wjplaud83 on 6/20/17.
 */

public interface MovieAPI {

    // this is to fetch movies with a specific sort
    @GET("/3/discover/movie")
    void fetchMovies(
            @Query("sort_by") String mSort,
            @Query("api_key") String mApiKey,
            @Query("language") String lang,
            Callback<movieGeneral> cb
    );

    @GET("/3/discover/reviews")
    void fetchReview(
            @Query("api_key") String mApiKey,
            @Path("id") String id,

            Callback<movieReview> cb
    );

    @GET("/3/discover/videos")
    void fetchVideos(
            @Query("api_key") String mApiKey,
            @Path("id") String id,
            Callback<movieYoutubeModel> cb

    );

}

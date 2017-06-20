package com.applaudstudios.android.moviesproject2.network;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by wjplaud83 on 6/20/17.
 */

public class NetworkAPI {
    public static final String API_BASE_URL = "http://api.themoviedb.org";

    private static RestAdapter.Builder builder = new RestAdapter.Builder()
            .setEndpoint(API_BASE_URL)
            .setClient(new OkClient(new OkHttpClient()));

    public static <S> S createService(Class<S> serviceClass) {
        RestAdapter adapter = builder.build();
        return adapter.create(serviceClass);
    }
}

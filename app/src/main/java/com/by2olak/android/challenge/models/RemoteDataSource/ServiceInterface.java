package com.by2olak.android.challenge.models.RemoteDataSource;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Hager.Magdy on 9/6/2017.
 */

public interface ServiceInterface {
    @GET
    Call<JsonObject> getPlaces(@Url String url, @QueryMap Map<String, String> options
    );
}

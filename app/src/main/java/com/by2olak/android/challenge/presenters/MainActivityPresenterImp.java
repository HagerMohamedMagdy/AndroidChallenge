package com.by2olak.android.challenge.presenters;

import android.content.Context;
import android.util.Log;

import com.by2olak.android.challenge.models.RemoteDataSource.ServiceInterface;
import com.by2olak.android.challenge.models.RemoteDataSource.ServerConfig;
import com.by2olak.android.challenge.models.data.MyApplication;
import com.by2olak.android.challenge.models.data.PlaceParser;
import com.by2olak.android.challenge.views.IMainActivityView;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hager.Magdy on 9/6/2017.
 */

public class MainActivityPresenterImp implements IMainActivityPresenter{
    IMainActivityView MainView;
    @Inject
    ServiceInterface mServiceApi;
    public MainActivityPresenterImp(Context context) {
        ((MyApplication)context).getAppComponent().inject(this);


    }
    @Override
    public void setView(IMainActivityView view) {
        this.MainView = view;
    }
    @Override
    public void searchBy2olakApi(String page,String size) {
        Log.e("searchBy2olakApi","called");
        MainView.showLoading();
        Map<String, String> mparams = new HashMap<String, String>();
        mparams.put("page", page);
        mparams.put("size", size);
      //  mparams.put("delay", "1000"); //optional for delay
        Call<JsonObject> call = mServiceApi.getPlaces(ServerConfig.By2olak_Service,mparams);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               MainView.hideLoading();
                /*
                JSONResponse jsonResponse = response.body();
                mArrayList = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));
                mAdapter = new DataAdapter(mArrayList);
                mRecyclerView.setAdapter(mAdapter);
                */
                Log.e("response is",response.body().toString()+"dd");
               MainView.updateBy2olakList( PlaceParser.parseJson(response));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                MainView.hideLoading();

            }
        });

    }



}

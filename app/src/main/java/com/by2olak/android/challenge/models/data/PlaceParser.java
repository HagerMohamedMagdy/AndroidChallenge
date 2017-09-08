package com.by2olak.android.challenge.models.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Response;

/**
 * Created by Hager.Magdy on 9/6/2017.
 */

public class PlaceParser {

    public static ArrayList<Place> parseJson(Response<JsonObject> response){
        ArrayList<Place> mPlaceList= new ArrayList<>();
        try {
            JSONObject mJsonObject= new JSONObject(response.body().toString());
            JSONArray mContentarray= mJsonObject.getJSONArray("content");
            for(int i=0;i<mContentarray.length();i++){
                Place mplace= new Place();
                mplace.setName(mContentarray.getJSONObject(i).getString("name"));
                mplace.setAddress(mContentarray.getJSONObject(i).getString("address"));
                mplace.setId(mContentarray.getJSONObject(i).getString("id"));
                mplace.setLang(mContentarray.getJSONObject(i).getString("longitude"));
                mplace.setLat(mContentarray.getJSONObject(i).getString("latitude"));
                mPlaceList.add(mplace);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
return mPlaceList;
    }


}

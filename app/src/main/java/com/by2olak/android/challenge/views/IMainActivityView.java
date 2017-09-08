package com.by2olak.android.challenge.views;

import com.by2olak.android.challenge.models.data.Place;

import java.util.ArrayList;

/**
 * Created by Hager.Magdy on 9/6/2017.
 */

public interface IMainActivityView {
    void showLoading();
    void hideLoading();
    void updateBy2olakList(ArrayList<Place> mPlaceList);
    void updateGooglekList(ArrayList<Place> mPlaceList);
}

package com.by2olak.android.challenge.presenters;

import com.by2olak.android.challenge.views.IMainActivityView;

/**
 * Created by Hager.Magdy on 9/6/2017.
 */

public interface IMainActivityPresenter {
     void setView(IMainActivityView view);

     void searchBy2olakApi(String page,String size);

}

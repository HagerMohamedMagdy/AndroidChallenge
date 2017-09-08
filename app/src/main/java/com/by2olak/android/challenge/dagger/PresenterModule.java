package com.by2olak.android.challenge.dagger;

import android.content.Context;

import com.by2olak.android.challenge.presenters.IMainActivityPresenter;
import com.by2olak.android.challenge.presenters.MainActivityPresenterImp;
import com.by2olak.android.challenge.views.IMainActivityView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hager.Magdy on 9/9/2017.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    IMainActivityPresenter provideMainPresenter(  Context context) {
        return new MainActivityPresenterImp(context);
    }
}

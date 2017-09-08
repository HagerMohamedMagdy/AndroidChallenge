package com.by2olak.android.challenge.dagger;

import com.by2olak.android.challenge.presenters.MainActivityPresenterImp;
import com.by2olak.android.challenge.views.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Hager.Magdy on 9/9/2017.
 */
@Singleton
@Component(modules = {AppModule.class, PresenterModule.class,RetrofitModule.class})
public interface AppComponent {
    void inject(MainActivity target);
    void inject(MainActivityPresenterImp target);
}

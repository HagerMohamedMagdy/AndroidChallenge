package com.by2olak.android.challenge.models.data;

import android.app.Application;

import com.by2olak.android.challenge.dagger.AppComponent;
import com.by2olak.android.challenge.dagger.AppModule;
import com.by2olak.android.challenge.dagger.DaggerAppComponent;

/**
 * Created by Hager.Magdy on 9/9/2017.
 */

public class MyApplication extends Application {
    private AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = initDagger(this);
    }
    protected AppComponent initDagger(MyApplication application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }
}

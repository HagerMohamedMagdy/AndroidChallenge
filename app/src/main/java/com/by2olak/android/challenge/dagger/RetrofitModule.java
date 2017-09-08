package com.by2olak.android.challenge.dagger;

import com.by2olak.android.challenge.models.RemoteDataSource.ServerConfig;
import com.by2olak.android.challenge.models.RemoteDataSource.ServiceInterface;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hager.Magdy on 9/9/2017.
 */
@Module
public class RetrofitModule {
    private static final String NAME_BASE_URL = "NAME_BASE_URL";
    @Provides
    @Named(NAME_BASE_URL)
    String provideBaseUrlString() {
        return ServerConfig.BY2OLAK_SERVER_URl;
    }

    @Provides
    @Singleton
    Converter.Factory provideGsonConverter() {
        return GsonConverterFactory.create();
    }
    @Provides
    @Singleton
    Retrofit provideRetrofit(Converter.Factory converter, @Named(NAME_BASE_URL) String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(converter)
                .build();
    }

    @Provides
    @Singleton
    ServiceInterface provideUsdaApi(Retrofit retrofit) {
        return retrofit.create(ServiceInterface.class);
    }
}

package com.elyseswoverland.circleconnect.dagger;

import android.content.Context;

import com.elyseswoverland.circleconnect.BuildConfig;
import com.elyseswoverland.circleconnect.network.CircleConnectApi;
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager;
import com.elyseswoverland.circleconnect.network.CircleConnectTypeAdapterFactory;
import com.elyseswoverland.circleconnect.network.interceptors.HeaderInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

@Module
public class NetworkModule {
    private static final String NAME_BASE_URL = "NAME_BASE_URL";

    @Provides
    @Singleton
    CircleConnectApi providesCircleConnectApi(Retrofit retrofit) {
        return retrofit.create(CircleConnectApi.class);
    }

    @Provides
    @Singleton
    CircleConnectApiManager providesCircleConnectApiManager(CircleConnectApi circleConnectApi) {
        return new CircleConnectApiManager(circleConnectApi);
    }

    @Provides
    @Named(NAME_BASE_URL)
    String providesBaseUrl() {
        return BuildConfig.SERVER_URL;
    }

    @Provides
    @Singleton
    HeaderInterceptor provideHeaderInterceptor(Context context) {
        return new HeaderInterceptor(context);
    }

    @Provides
    @Singleton
    Gson providesGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .registerTypeAdapterFactory(new CircleConnectTypeAdapterFactory())
                .registerTypeAdapter(Date.class, (JsonSerializer<Date>) (src, typeOfSrc, context)
                        -> src == null ? null : new JsonPrimitive(src.getTime()))
                .registerTypeAdapter(Date.class, (JsonDeserializer<Date>) (json, typeOfT, context)
                        -> new Date(json.getAsJsonPrimitive().getAsLong()))
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .setLenient()
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient providesOkHttpClient(HeaderInterceptor headerInterceptor) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(headerInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    Retrofit providesRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .baseUrl(BuildConfig.SERVER_URL)
                .build();
    }
}

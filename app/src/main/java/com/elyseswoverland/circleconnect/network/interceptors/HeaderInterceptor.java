package com.elyseswoverland.circleconnect.network.interceptors;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.elyseswoverland.circleconnect.app.Constants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {
    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String token = prefs.getString(Constants.AUTH_TOKEN, null);

        if (token == null || chain.request().url().toString().contains("session")) {
            Request.Builder request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .method(chain.request().method(), chain.request().body());

            return chain.proceed(request.build());
        } else {
            Request.Builder request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .method(chain.request().method(), chain.request().body());
            return chain.proceed(request.build());
        }
    }
}

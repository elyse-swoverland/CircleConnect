package com.elyseswoverland.circleconnect.app;

import android.app.Application;

import com.elyseswoverland.circleconnect.dagger.Dagger;

public class CircleConnectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Dagger.init(this);
    }
}

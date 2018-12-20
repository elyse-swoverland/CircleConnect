package com.elyseswoverland.circleconnect.dagger;

import com.elyseswoverland.circleconnect.CircleConnectApplication;

import dagger.Module;

@Module
public class ApplicationModule {

    private final CircleConnectApplication application;

    public ApplicationModule(CircleConnectApplication application) {
        this.application = application;
    }
}

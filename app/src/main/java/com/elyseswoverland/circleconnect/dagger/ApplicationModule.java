package com.elyseswoverland.circleconnect.dagger;

import android.app.Application;
import android.content.Context;

import com.elyseswoverland.circleconnect.app.CircleConnectApplication;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final CircleConnectApplication application;

    public ApplicationModule(CircleConnectApplication application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application.getApplicationContext();
    }

    @Provides
    public Application providesApplication() {
        return application;
    }
}

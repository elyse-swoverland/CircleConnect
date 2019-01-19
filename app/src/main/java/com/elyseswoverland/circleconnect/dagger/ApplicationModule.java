package com.elyseswoverland.circleconnect.dagger;

import android.app.Application;
import android.content.Context;

import com.elyseswoverland.circleconnect.app.CircleConnectApplication;
import com.elyseswoverland.circleconnect.persistence.AppPreferences;
import com.elyseswoverland.circleconnect.persistence.SessionStorage;

import javax.inject.Singleton;

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

    @Provides
    @Singleton
    AppPreferences providesAppPreferences(Application application) {
        return new AppPreferences(application.getSharedPreferences("general", Context.MODE_PRIVATE));
    }

    @Provides
    @Singleton
    SessionStorage providesSessionStorage() {
        return new SessionStorage();
    }
}

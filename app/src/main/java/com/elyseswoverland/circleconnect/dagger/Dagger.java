package com.elyseswoverland.circleconnect.dagger;

import com.elyseswoverland.circleconnect.CircleConnectApplication;

public final class Dagger {

    public static final String TAG = Dagger.class.getSimpleName();

    private static final Dagger INSTANCE = new Dagger();

    private ApplicationComponent component;

    private Dagger() {}

    public static void init(CircleConnectApplication application) {
        INSTANCE.component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(application))
                .build();
    }

    public static Dagger getInstance() {
        return INSTANCE;
    }

    public ApplicationComponent component() {
        return component;
    }
}
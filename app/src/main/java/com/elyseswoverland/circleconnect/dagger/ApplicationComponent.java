package com.elyseswoverland.circleconnect.dagger;

import com.elyseswoverland.circleconnect.ui.map.MapFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(MapFragment mapFragment);

}

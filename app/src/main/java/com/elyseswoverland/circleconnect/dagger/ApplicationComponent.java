package com.elyseswoverland.circleconnect.dagger;

import com.elyseswoverland.circleconnect.ui.MainActivity;
import com.elyseswoverland.circleconnect.ui.account.AccountFragment;
import com.elyseswoverland.circleconnect.ui.favorites.FavoritesFragment;
import com.elyseswoverland.circleconnect.ui.login.LoginFragment;
import com.elyseswoverland.circleconnect.ui.map.MapFragment;
import com.elyseswoverland.circleconnect.ui.messages.MessagesFragment;
import com.elyseswoverland.circleconnect.ui.preferences.PreferencesActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
    void inject(MapFragment mapFragment);
    void inject(LoginFragment loginFragment);
    void inject(MessagesFragment messagesFragment);
    void inject(FavoritesFragment favoritesFragment);
    void inject(PreferencesActivity preferencesActivity);
    void inject(AccountFragment accountFragment);
}

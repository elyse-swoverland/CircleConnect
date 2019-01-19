package com.elyseswoverland.circleconnect.persistence;

import android.content.SharedPreferences;

import com.facebook.AccessToken;

public class AppPreferences {
    private final SharedPreferences sharedPreferences;
    private static final String FB_ACCESS_TOKEN = "FB_ACCESS_TOKEN";

    public AppPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setFbAccessToken(AccessToken accessToken) {
        sharedPreferences.edit().putString(FB_ACCESS_TOKEN, accessToken.getToken()).apply();
    }

    public String getFbAccessToken() {
        return sharedPreferences.getString(FB_ACCESS_TOKEN, null);
    }
}

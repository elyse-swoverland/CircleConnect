package com.elyseswoverland.circleconnect.persistence;

import android.content.SharedPreferences;

import com.elyseswoverland.circleconnect.app.Constants;
import com.facebook.AccessToken;

public class AppPreferences {
    private final SharedPreferences sharedPreferences;
    private static final String FB_ACCESS_TOKEN = "FB_ACCESS_TOKEN";
    private static final String CUST_ID = "CUST_ID";

    public AppPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void setFbAccessToken(AccessToken accessToken) {
        sharedPreferences.edit().putString(FB_ACCESS_TOKEN, accessToken.getToken()).apply();
    }

    public String getFbAccessToken() {
        return sharedPreferences.getString(FB_ACCESS_TOKEN, null);
    }

    public void setToken(String token) {
        sharedPreferences.edit().putString(Constants.AUTH_TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(Constants.AUTH_TOKEN, null);
    }

    public boolean hasToken() {
        return getToken() != null;
    }

    public void setCustId(int custId) {
        sharedPreferences.edit().putInt(CUST_ID, custId).apply();
    }

    public int getCustId() {
        return sharedPreferences.getInt(CUST_ID, 0);
    }
}

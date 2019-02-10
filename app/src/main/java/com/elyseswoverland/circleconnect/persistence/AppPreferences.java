package com.elyseswoverland.circleconnect.persistence;

import android.content.SharedPreferences;

import com.elyseswoverland.circleconnect.app.Constants;
import com.facebook.AccessToken;

public class AppPreferences {
    private final SharedPreferences sharedPreferences;
    private static final String FB_ACCESS_TOKEN = "FB_ACCESS_TOKEN";
    private static final String CUST_ID = "CUST_ID";
    private static final String RECENT_LAT = "RECENT_LAT";
    private static final String RECENT_LONG = "RECENT_LONG";

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

    public void setRecentLat(double latitude) {
        sharedPreferences.edit().putLong(RECENT_LAT, Double.doubleToRawLongBits(latitude)).apply();
    }

    public double getRecentLat() {
        return Double.longBitsToDouble(sharedPreferences.getLong(RECENT_LAT, Double.doubleToLongBits(0)));

    }

    public void setRecentLong(double longitude) {
        sharedPreferences.edit().putLong(RECENT_LONG, Double.doubleToRawLongBits(longitude)).apply();
    }

    public double getRecentLong() {
        return Double.longBitsToDouble(sharedPreferences.getLong(RECENT_LONG, Double.doubleToLongBits(0)));

    }
}

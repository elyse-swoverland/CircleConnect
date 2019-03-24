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
    private static final String SORT_PREF_POSITION = "SORT_PREF_POSITION";
    private static final String DISTANCE_PREF = "DISTANCE_PREF";

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

    public void setSortByPreference(int sortPreference) {
        sharedPreferences.edit().putInt(SORT_PREF_POSITION, sortPreference).apply();
    }

    public int getSortByPreference() {
        return sharedPreferences.getInt(SORT_PREF_POSITION, 0);
    }

    public void setDistancePreference(int distancePreference) {
        sharedPreferences.edit().putInt(DISTANCE_PREF, distancePreference).apply();
    }

    public int getDistancePreference() {
        return sharedPreferences.getInt(DISTANCE_PREF, 20);
    }
}

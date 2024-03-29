package com.elyseswoverland.circleconnect.network;

import com.elyseswoverland.circleconnect.models.CustomerSetting;
import com.elyseswoverland.circleconnect.models.Merchant;
import com.elyseswoverland.circleconnect.models.MessageResponse;
import com.elyseswoverland.circleconnect.models.Profile;
import com.elyseswoverland.circleconnect.models.Session;
import com.elyseswoverland.circleconnect.models.SessionRequest;
import com.elyseswoverland.circleconnect.models.UpdateCustFavoritesRequest;

import java.util.ArrayList;

import javax.inject.Inject;

import rx.Observable;

public class CircleConnectApiManager {
    public static final String TAG = CircleConnectApiManager.class.getSimpleName();

    private CircleConnectApi circleConnectApi;

    @Inject
    public CircleConnectApiManager(CircleConnectApi circleConnectApi) {
        this.circleConnectApi = circleConnectApi;
    }

    public Observable<Session> startSession(final SessionRequest sessionRequest) {
        return circleConnectApi.startSession(sessionRequest);
    }

    public Observable<ArrayList<Merchant>> getMerchants(int customerId, double custLatitude, double custLongitude, double latitude, double longitude, int radius) {
        return circleConnectApi.getMerchants(customerId, custLatitude, custLongitude, latitude, longitude, radius);
    }

    public Observable<MessageResponse> getCustomerMessages(String messageDate) {
        return circleConnectApi.getCustomerMessages(messageDate);
    }

    public Observable<Boolean> updateCustomerFavorites(UpdateCustFavoritesRequest request) {
        return circleConnectApi.updateCustomerFavorites(request);
    }

    public Observable<ArrayList<Merchant>> getCustomerFavorites(double latitude, double longitude) {
        return circleConnectApi.getCustomerFavorites(latitude, longitude);
    }

    public Observable<CustomerSetting> getCustomerPrefs() {
        return circleConnectApi.getCustomerSettings();
    }

    public Observable<Boolean> updateCustomerPrefs(CustomerSetting customerSetting) {
        return circleConnectApi.updateCustomerSetting(customerSetting);
    }

    public Observable<Profile> getUserProfile() {
        return circleConnectApi.getUserProfile();
    }

    public Observable<Boolean> setUserProfile(Profile profile) {
        return circleConnectApi.setUserProfile(profile);
    }
}

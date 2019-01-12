package com.elyseswoverland.circleconnect.network;

import com.elyseswoverland.circleconnect.models.Merchant;
import com.elyseswoverland.circleconnect.models.Session;
import com.elyseswoverland.circleconnect.models.SessionRequest;

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

    public Observable<ArrayList<Merchant>> getMerchants(int customerId, double latitude, double longitude, int radius) {
        return circleConnectApi.getMerchants(customerId, latitude, longitude, radius);
    }
}
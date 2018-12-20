package com.elyseswoverland.circleconnect.network;

import com.elyseswoverland.circleconnect.models.CustomerSetting;
import com.elyseswoverland.circleconnect.models.Merchant;
import com.elyseswoverland.circleconnect.models.Session;
import com.elyseswoverland.circleconnect.models.SessionRequest;
import com.elyseswoverland.circleconnect.models.SortPreferenceType;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface CircleConnectApi {
    @POST("/api/Session")
    Observable<Session> startSession(@Body SessionRequest sessionRequest);

    @GET("/api/SortPreference")
    Observable<SortPreferenceType> getSortPreference();

    @GET("/api/CustomerSetting")
    Observable<CustomerSetting> getCustomerSettings();

    @POST("/api/CustomerSetting")
    Observable<Void> postCustomerSetting(@Body CustomerSetting customerSetting);

    @GET("/api/Merchants")
    Observable<ArrayList<Merchant>> getMerchants(@Query("customerId") int customerId,
                                                 @Query("latitude") double latitude,
                                                 @Query("longitude") double longitude,
                                                 @Query("radius") int radius);
}

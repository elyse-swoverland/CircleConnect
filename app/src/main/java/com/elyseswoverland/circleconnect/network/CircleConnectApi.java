package com.elyseswoverland.circleconnect.network;

import com.elyseswoverland.circleconnect.network.models.CustomerSetting;
import com.elyseswoverland.circleconnect.network.models.Merchants;
import com.elyseswoverland.circleconnect.network.models.Session;
import com.elyseswoverland.circleconnect.network.models.SessionRequest;
import com.elyseswoverland.circleconnect.network.models.SortPreferenceType;

import java.math.BigDecimal;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface CircleConnectApi {
    @POST("/api/Session")
    Observable<Session> getSession(@Body SessionRequest sessionRequest);

    @GET("/api/SortPreference")
    Observable<SortPreferenceType> getSortPreference();

    @GET("/api/CustomerSetting")
    Observable<CustomerSetting> getCustomerSettings();

    @POST("/api/CustomerSetting")
    Observable<Void> postCustomerSetting(@Body CustomerSetting customerSetting);

    @GET("/api/Merchants{customerId}{latitude}{longitude}{radius}")
    Observable<Merchants> getMerchants(@Path("customerId") int customerId,
                                       @Path("latitude")BigDecimal latitude,
                                       @Path("longitude") BigDecimal longitude,
                                       @Path("radius") int radius);
}

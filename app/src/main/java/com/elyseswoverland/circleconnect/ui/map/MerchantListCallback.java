package com.elyseswoverland.circleconnect.ui.map;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public interface MerchantListCallback {
    void moveMap(LatLng latLng);

    void showInfoWindow(Marker marker);

    void collapseSlidingPanel();

    void updateFavorite(int merchId, boolean turnOn);
}

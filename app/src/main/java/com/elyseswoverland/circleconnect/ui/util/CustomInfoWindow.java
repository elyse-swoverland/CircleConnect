package com.elyseswoverland.circleconnect.ui.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.elyseswoverland.circleconnect.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context context;

    public CustomInfoWindow(Context ctx) {
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.layout_custom_info_window, null);

        TextView merchantName = view.findViewById(R.id.merchantName);
        TextView merchantPhone = view.findViewById(R.id.merchantPhone);

        merchantName.setText(marker.getTitle());

        return view;
    }
}

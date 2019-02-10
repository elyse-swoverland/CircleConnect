package com.elyseswoverland.circleconnect.ui.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.elyseswoverland.circleconnect.R;
import com.elyseswoverland.circleconnect.models.Merchant;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private Merchant merchant;

    public CustomInfoWindow(Context ctx, Merchant merchant) {
        context = ctx;
        this.merchant = merchant;
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
        if (merchant.getBusinessPhone() != null) {
            merchantPhone.setVisibility(View.VISIBLE);
            merchantPhone.setText(merchant.getBusinessPhone());
        } else {
            merchantPhone.setVisibility(View.GONE);
        }

        return view;
    }
}

package com.elyseswoverland.circleconnect.ui.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
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
        View view = ((Activity) context).getLayoutInflater()
                .inflate(R.layout.layout_custom_info_window, null);

        TextView merchantName = view.findViewById(R.id.merchantName);
        TextView merchantPhone = view.findViewById(R.id.merchantPhone);
        TextView merchantContact = view.findViewById(R.id.merchantContact);
        TextView merchantAddress = view.findViewById(R.id.merchantAddress);
        ImageView merchantLogo = view.findViewById(R.id.merchantLogo);

        merchantName.setText(marker.getTitle());
        if (merchant.getBusinessPhone() != null) {
            merchantPhone.setVisibility(View.VISIBLE);
            merchantPhone.setText(merchant.getBusinessPhone());
        } else {
            merchantPhone.setVisibility(View.GONE);
        }

//        if (merchant.getAddress() != null) {
//            merchantAddress.setText(merchant.getAddress());
//        }
//
//        if (merchant.getContactName() != null) {
//            merchantContact.setText(merchant.getContactName());
//        } else {
//            merchantContact.setVisibility(View.GONE);
//        }
//
//        if (merchant.getLogo() != null) {
//            Bitmap bitmap = stringToBitmap(merchant.getLogo());
//            Log.d("TAG", "Bitmap: " + bitmap);
//            merchantLogo.setImageBitmap(bitmap);
//        }

        return view;
    }

    private Bitmap stringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;

        }
    }
}

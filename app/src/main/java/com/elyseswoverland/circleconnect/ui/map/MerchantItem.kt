package com.elyseswoverland.circleconnect.ui.map

import android.content.Context
import android.location.Location
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.models.Merchant
import com.google.android.gms.maps.model.LatLng
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_merchant.*


class MerchantItem constructor(private val context: Context,
                               private val merchant: Merchant) : Item() {

    override fun getLayout(): Int = com.elyseswoverland.circleconnect.R.layout.item_merchant

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.merchantName.text = merchant.merchName
        viewHolder.merchantAddress.text = String.format(context.getString(R.string.address),
                merchant.city,
                merchant.state,
                merchant.zipCode)

        // TODO: - Clean up
        val latLngA = LatLng(39.875605, -86.082856)
        val latLngB = LatLng(merchant.merchLocation.latitude, merchant.merchLocation.longitude)

        val locationA = Location("point A")
        locationA.latitude = latLngA.latitude
        locationA.longitude = latLngA.longitude
        val locationB = Location("point B")
        locationB.latitude = latLngB.latitude
        locationB.longitude = latLngB.longitude

        val distance = locationA.distanceTo(locationB)
        val distanceInMiles = distance * 0.000621371

        viewHolder.distance.text = String.format("%.2f", distanceInMiles)
    }
}
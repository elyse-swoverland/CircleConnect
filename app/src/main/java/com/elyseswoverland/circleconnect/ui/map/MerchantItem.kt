package com.elyseswoverland.circleconnect.ui.map

import android.content.Context
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.models.Merchant
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_merchant.*


class MerchantItem constructor(private val context: Context,
                               private val merchant: Merchant,
                               private val m: Marker,
                               private val callback: MerchantListCallback) : Item() {

    override fun getLayout(): Int = com.elyseswoverland.circleconnect.R.layout.item_merchant

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.merchantName.text = merchant.merchName
        viewHolder.merchantAddress.text = String.format(context.getString(R.string.address),
                merchant.city,
                merchant.state,
                merchant.zipCode)

        viewHolder.distance.text = String.format(context.getString(R.string.distance_from_customer),
                merchant.distanceFromCustomer)

//        viewHolder.distance.text = String.format("%.2f", merchant.distanceFromCustomer)

        viewHolder.rootLayout.setOnClickListener {
            val latLngB = LatLng(merchant.merchLocation.latitude, merchant.merchLocation.longitude)
            callback.moveMap(latLngB)
            callback.showInfoWindow(m)
            callback.collapseSlidingPanel()
        }
    }
}
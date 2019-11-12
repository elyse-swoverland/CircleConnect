package com.elyseswoverland.circleconnect.ui.map

import android.content.Context
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.models.Merchant
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

        if (merchant.custFavorite) {
            viewHolder.favoriteButton.setImageDrawable(context.getDrawable(R.drawable.ic_star_yellow_36dp))
        } else {
            viewHolder.favoriteButton.setImageDrawable(context.getDrawable(R.drawable.ic_star_gray_36dp))
        }

        viewHolder.favoriteButton.setOnClickListener {
            if (merchant.custFavorite) {
                viewHolder.favoriteButton.setImageDrawable(context.getDrawable(R.drawable.ic_star_gray_36dp))
                callback.updateFavorite(merchant.merchId, false)
            } else {
                viewHolder.favoriteButton.setImageDrawable(context.getDrawable(R.drawable.ic_star_yellow_36dp))
                callback.updateFavorite(merchant.merchId, true)
            }
        }

        viewHolder.rootLayout.setOnClickListener {
//            val latLngB = LatLng(merchant.merchLocation.latitude, merchant.merchLocation.longitude)
//            callback.moveMap(latLngB)
//            callback.showInfoWindow(m)
//            callback.collapseSlidingPanel()
            callback.goToBusinessCard(merchant)
        }
    }
}
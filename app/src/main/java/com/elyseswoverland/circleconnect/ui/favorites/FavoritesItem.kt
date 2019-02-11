package com.elyseswoverland.circleconnect.ui.favorites

import android.content.Context
import com.elyseswoverland.circleconnect.models.Merchant
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_favorite.*

class FavoritesItem constructor(private val context: Context,
                                private val merchant: Merchant) : Item() {

    override fun getLayout(): Int = com.elyseswoverland.circleconnect.R.layout.item_favorite

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.merchantName.text = merchant.merchName
        viewHolder.merchantType.text = merchant.merchType.merchTypeDescription
    }

}
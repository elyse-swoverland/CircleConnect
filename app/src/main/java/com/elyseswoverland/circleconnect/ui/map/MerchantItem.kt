package com.elyseswoverland.circleconnect.ui.map

import android.content.Context
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.models.Merchant
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_merchant.*

class MerchantItem constructor(private val context: Context,
                               private val merchant: Merchant) : Item() {

    override fun getLayout(): Int = R.layout.item_merchant

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.merchantName.text = merchant.merchName
        viewHolder.merchantAddress.text = merchant.city
    }
}
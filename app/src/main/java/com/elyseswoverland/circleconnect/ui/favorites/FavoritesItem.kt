package com.elyseswoverland.circleconnect.ui.favorites

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.elyseswoverland.circleconnect.models.Merchant
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_favorite.*

class FavoritesItem constructor(private val context: Context,
                                private val merchant: Merchant,
                                private val callback: FavoritesCallback) : Item() {

    override fun getLayout(): Int = com.elyseswoverland.circleconnect.R.layout.item_favorite

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.merchantName.text = merchant.merchName
        viewHolder.merchantType.text = merchant.merchType.merchTypeDescription

        merchant.logo?.let {
            viewHolder.merchantLogo.setImageBitmap(stringToBitmap(merchant.logo))
        }

        viewHolder.infoIcon.setOnClickListener {
            callback.goToBusinessCard(merchant)
        }
    }

    private fun stringToBitmap(encodedString: String): Bitmap? {
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e: Exception) {
            e.message
            null
        }

    }
}
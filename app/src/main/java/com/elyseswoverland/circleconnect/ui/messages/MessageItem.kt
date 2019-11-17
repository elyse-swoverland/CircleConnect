package com.elyseswoverland.circleconnect.ui.messages

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.elyseswoverland.circleconnect.models.Message
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_message.*
import java.text.SimpleDateFormat
import java.util.*

class MessageItem constructor(private val context: Context,
                              private val message: Message) : Item() {

    override fun getLayout(): Int = com.elyseswoverland.circleconnect.R.layout.item_message

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.merchantName.text = message.merchant.merchName
        viewHolder.messageTitle.text = message.merchMessage.subject
        viewHolder.message.text = message.merchMessage.message

        val date = Date()
        val dateFormat = SimpleDateFormat("MMM dd", Locale.US)
        val formattedDate = dateFormat.format(date)

        viewHolder.messageDate.text = formattedDate

//        message.merchant.logo?.let {
//            viewHolder.merchantLogo.setImageBitmap(stringToBitmap(message.merchant.logo))
//        }
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
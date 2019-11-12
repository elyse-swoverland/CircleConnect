package com.elyseswoverland.circleconnect.ui.businesscard

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.models.Merchant
import kotlinx.android.synthetic.main.activity_business_card.*

class BusinessCardActivity: AppCompatActivity() {
    private lateinit var merchant: Merchant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_card)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

        merchant = intent.getParcelableExtra("MERCHANT")

        businessName.text = merchant.merchName
        address.text = merchant.address
        hours.text = merchant.hours
        phone.text = merchant.businessPhone
        distance.text = String.format(getString(R.string.distance_from_customer),
                merchant.distanceFromCustomer)
        merchant.logo?.let {
            merchantLogo.setImageBitmap(stringToBitmap(it))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
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

    companion object {
        fun newIntent(context: Context, merchant: Merchant): Intent {
            val intent = Intent(context, BusinessCardActivity::class.java)
            val args = Bundle()
            args.putParcelable("MERCHANT", merchant)
            intent.putExtras(args)
            return intent
        }
    }
}
package com.elyseswoverland.circleconnect.ui.businesscard

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.models.UpdateCustFavoritesRequest
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import kotlinx.android.synthetic.main.activity_business_card.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BusinessCardActivity: AppCompatActivity() {
    private lateinit var merchant: Merchant

    @Inject
    lateinit var circleConnectApiManager: CircleConnectApiManager

    @Inject
    lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_card)
        Dagger.getInstance().component().inject(this)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)

        merchant = intent.getParcelableExtra("MERCHANT")

        businessName.text = merchant.merchName
        address.text = merchant.address
        hours.text = merchant.hours
        phone.text = merchant.businessPhone
        contactName.text = merchant.contactName
        distance.text = String.format(getString(R.string.distance_from_customer),
                merchant.distanceFromCustomer)
        merchant.logo?.let {
            merchantLogo.setImageBitmap(stringToBitmap(it))
        }

        if (merchant.custFavorite) {
            removeFromFavoritesButtonText()
        } else {
            addToFavoritesButtonText()
        }

        favoriteButton.setOnClickListener {
            if (merchant.custFavorite) {
                addToFavoritesButtonText()
                updateFavorite(merchant.merchId, false)
            } else {
                removeFromFavoritesButtonText()
                updateFavorite(merchant.merchId, true)
            }
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

    private fun onUpdateFavoriteSuccess(isFavorite: Boolean) {

    }

    private fun onUpdateFavoriteFailure(throwable: Throwable) {

    }

    private fun updateFavorite(merchId: Int, turnOn: Boolean) {
        val request = UpdateCustFavoritesRequest(merchId, turnOn)
        circleConnectApiManager.updateCustomerFavorites(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdateFavoriteSuccess, this::onUpdateFavoriteFailure)
    }

    private fun addToFavoritesButtonText() {
        favoriteButton.text = "Add to Favorites"
        favoriteButton.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
    }

    private fun removeFromFavoritesButtonText() {
        favoriteButton.text = "Remove from Favorites"
        favoriteButton.setTextColor(ContextCompat.getColor(this, R.color.darker_red))
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
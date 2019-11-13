package com.elyseswoverland.circleconnect.ui.businesscard

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.models.UpdateCustFavoritesRequest
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.elyseswoverland.circleconnect.ui.util.CustomInfoWindow
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_business_card.*
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class BusinessCardActivity: AppCompatActivity(), OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {
    private lateinit var merchant: Merchant
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

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

        mMapView = mapView
        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        try {
            MapsInitializer.initialize(applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        businessName.text = merchant.merchName
        address.text = merchant.address
        hours.text = merchant.hours
        distance.text = String.format(getString(R.string.distance_from_customer),
                merchant.distanceFromCustomer)
        merchant.logo?.let {
            merchantLogo.setImageBitmap(stringToBitmap(it))
        }

        if (merchant.businessPhone != null) {
            phone.text = merchant.businessPhone
        } else {
            phone.visibility = View.GONE
        }
        if (merchant.contactName != null) {
            contactName.text = merchant.contactName
        } else {
            contactName.visibility = View.GONE
        }
        if (merchant.merchMessage != null) {
            merchantMessage.text = merchant.merchMessage!!.message
        } else {
            messagesLayout.visibility = View.GONE
            messagesDivider.visibility = View.GONE
        }

        if (merchant.custFavorite) {
            removeFromFavoritesButtonText()
        } else {
            addToFavoritesButtonText()
        }

        directionsIcon.setOnClickListener {
            val uri = "http://maps.google.com/maps?q=loc:" + merchant.merchLocation.latitude + "," + merchant.merchLocation.longitude + " (" + merchant.merchName + ")"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent)
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

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setupMap(merchant)
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupMap(merchant: Merchant) {
        val customInfoWindow = CustomInfoWindow(this, merchant)
        mMap.setInfoWindowAdapter(customInfoWindow)
        val m = mMap.addMarker(MarkerOptions().position(LatLng(merchant.merchLocation.latitude,
                merchant.merchLocation.longitude)).title(merchant.merchName).snippet(merchant.description))
        val currentLatLng = LatLng(merchant.merchLocation.latitude, merchant.merchLocation.longitude)
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 18f))
        m.showInfoWindow()
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
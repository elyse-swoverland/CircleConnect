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
import com.elyseswoverland.circleconnect.models.MerchLocation
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
    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var merchName: String
    private lateinit var merchAddress: String
    private lateinit var merchHours: String
    private lateinit var merchDescription: String
    private lateinit var merchLocation: MerchLocation
    private var merchId: Int = 0
    private var merchDistance: Double = 0.0
    private var merchPhone: String? = null
    private var merchContactName: String? = null
    private var merchMessage: String? = null
    private var custFavorite: Boolean = false

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

        merchName = intent.getStringExtra("MERCH_NAME")
        merchAddress = intent.getStringExtra("ADDRESS")
        merchHours = intent.getStringExtra("HOURS")
        merchDistance = intent.getDoubleExtra("DISTANCE", 0.0)
        merchPhone = intent.getStringExtra("PHONE")
        merchContactName = intent.getStringExtra("CONTACT_NAME")
        merchMessage = intent.getStringExtra("MESSAGE")
        custFavorite = intent.getBooleanExtra("CUST_FAVORITE", false)
        merchLocation = intent.getParcelableExtra("MERCH_LOCATION")
        merchId = intent.getIntExtra("MERCH_ID", 0)
        merchDescription = intent.getStringExtra("MERCH_DESCRIPTION")


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

        businessName.text = merchName
        address.text = merchAddress
        hours.text = merchHours
        distance.text = String.format(getString(R.string.distance_from_customer),
                merchDistance)
        appPreferences.merchLogo?.let {
            merchantLogo.setImageBitmap(stringToBitmap(it))
        }

        if (merchPhone != null) {
            phone.text = merchPhone
        } else {
            phone.visibility = View.GONE
        }
        if (merchContactName != null) {
            contactName.text = merchContactName
        } else {
            contactName.visibility = View.GONE
        }
        if (merchMessage != null) {
            merchantMessage.text = merchMessage
        } else {
            messagesLayout.visibility = View.GONE
            messagesDivider.visibility = View.GONE
        }

        if (custFavorite) {
            removeFromFavoritesButtonText()
        } else {
            addToFavoritesButtonText()
        }

        directionsIcon.setOnClickListener {
            val uri = "http://maps.google.com/maps?q=loc:" + merchLocation.latitude + "," + merchLocation.longitude + " (" + merchName + ")"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent)
        }

        favoriteButton.setOnClickListener {
            if (custFavorite) {
                addToFavoritesButtonText()
                updateFavorite(merchId, false)
            } else {
                removeFromFavoritesButtonText()
                updateFavorite(merchId, true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setupMap(merchLocation, merchName, merchDescription)
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

    private fun setupMap(merchLocation: MerchLocation, merchName: String, merchDescription: String) {
        val customInfoWindow = CustomInfoWindow(this)
        mMap.setInfoWindowAdapter(customInfoWindow)
        val m = mMap.addMarker(MarkerOptions().position(LatLng(merchLocation.latitude,
                merchLocation.longitude)).title(merchName).snippet(merchDescription))
        val currentLatLng = LatLng(merchLocation.latitude, merchLocation.longitude)
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
        fun newIntent(context: Context, merchName: String, address: String, hours: String,
                      distance: Double, phone: String?, contactName: String?, message: String?,
                      custFavorite: Boolean, merchLocation: MerchLocation, merchId: Int, merchDescription: String): Intent {
            val intent = Intent(context, BusinessCardActivity::class.java)
            val args = Bundle()
            args.putString("MERCH_NAME", merchName)
            args.putString("ADDRESS", address)
            args.putString("HOURS", hours)
            args.putDouble("DISTANCE", distance)
            args.putString("PHONE", phone)
            args.putString("CONTACT_NAME", contactName)
            args.putString("MESSAGE", message)
            args.putBoolean("CUST_FAVORITE", custFavorite)
            args.putParcelable("MERCH_LOCATION", merchLocation)
            args.putInt("MERCH_ID", merchId)
            args.putString("MERCH_DESCRIPTION", merchDescription)

            intent.putExtras(args)
            return intent
        }
    }
}
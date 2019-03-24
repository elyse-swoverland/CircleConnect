package com.elyseswoverland.circleconnect.ui.map

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.models.UpdateCustFavoritesRequest
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.persistence.AppPreferences
import com.elyseswoverland.circleconnect.ui.preferences.PreferencesActivity
import com.elyseswoverland.circleconnect.ui.util.CustomInfoWindow
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_map.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject




class MapFragment : Fragment(), OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener, MerchantListCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var ctx: Context
    private var lastLocation: Location? = null
    private var callback: MerchantListCallback? = null
    private val groupAdapter = GroupAdapter<com.xwray.groupie.ViewHolder>()

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    @Inject lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        Dagger.getInstance().component().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)
        mMapView = rootView.findViewById(R.id.mapView)

        mMapView.onCreate(savedInstanceState)
        mMapView.onResume()

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mMapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        ctx = context ?: return

        this.callback = this
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
        menu?.findItem(R.id.logout)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.preferences -> {
                startActivity(Intent(context, PreferencesActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    private fun onGetMerchantsSuccess(merchants: ArrayList<Merchant>) {
        groupAdapter.clear()
        groupAdapter.add(Section().apply {
            merchants.forEachIndexed { _, merchant ->

//                val bitmap = stringToBitmap(merchant.logo!!)
//                val merchantName = merchant.merchName
//                Log.d("TAG", "Bitmap: $bitmap")
//                Log.d("TAG", "Name: $merchantName")

                val customInfoWindow = CustomInfoWindow(ctx, merchant)
                mMap.setInfoWindowAdapter(customInfoWindow)
                val m = mMap.addMarker(MarkerOptions().position(LatLng(merchant.merchLocation.latitude,
                        merchant.merchLocation.longitude)).title(merchant.merchName))
                add(MerchantItem(ctx, merchant, m, callback!!))
            }
        })
        recyclerView.adapter = groupAdapter

    }

    private fun onGetMerchantsFailure(throwable: Throwable) {
        Log.d("TAG", "Fail :(")
        throwable.printStackTrace()
    }

    private fun onUpdateFavoriteSuccess(isFavorite: Boolean) {

    }

    private fun onUpdateFavoriteFailure(throwable: Throwable) {

    }

    private fun setUpRecyclerView() {
        val lm = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = lm
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, lm.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        setUpMap()
    }

    override fun onMarkerClick(p0: Marker?) = false

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            LOCATION_PERMISSIONS -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpMap()
                }
            }
        }
    }

    private fun setUpMap() {
        if (checkSelfPermission(context!!,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSIONS)
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                appPreferences.recentLat = location.latitude
                appPreferences.recentLong = location.longitude
                circleConnectApiManager.getMerchants(appPreferences.custId, location.latitude,
                        location.longitude, appPreferences.distancePreference)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::onGetMerchantsSuccess, this::onGetMerchantsFailure)

                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))

                val geocoder = Geocoder(ctx, Locale.getDefault())
                val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                val address = addresses[0]
                currentLocationAddress.text = String.format(getString(R.string.current_location_address),
                        address.subThoroughfare,
                        address.thoroughfare,
                        address.locality,
                        address.adminArea,
                        address.postalCode)
            }
        }
    }

    override fun moveMap(latLng: LatLng?) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
    }

    override fun showInfoWindow(marker: Marker?) {
        marker?.showInfoWindow()
    }

    override fun collapseSlidingPanel() {
        sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
    }

    override fun updateFavorite(merchId: Int, turnOn: Boolean) {
        val request = UpdateCustFavoritesRequest(merchId, turnOn)
        circleConnectApiManager.updateCustomerFavorites(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onUpdateFavoriteSuccess, this::onUpdateFavoriteFailure)
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
        private const val LOCATION_PERMISSIONS = 99
    }
}
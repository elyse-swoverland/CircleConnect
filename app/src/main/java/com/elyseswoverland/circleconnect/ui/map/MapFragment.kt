package com.elyseswoverland.circleconnect.ui.map

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject


class MapFragment : Fragment(), OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    @Inject lateinit var circleConnectApiManager: CircleConnectApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onResume() {
        super.onResume()
        mMapView.onResume()

        circleConnectApiManager.getMerchants(0, 39.8717418, -86.141082, 1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetMerchantsSuccess, this::onGetMerchantsFailure)
    }

    private fun onGetMerchantsSuccess(merchants: ArrayList<Merchant>) {
        Log.d("TAG", "Success!")
    }

    private fun onGetMerchantsFailure(throwable: Throwable) {
        Log.d("TAG", "Fail :(")
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
        // Add a marker and move the camera
        val bierBrewery = LatLng(39.875605, -86.082856)
        mMap.addMarker(MarkerOptions().position(bierBrewery).title("Bier Brewery"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bierBrewery, 10f))
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))

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
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f))
            }
        }
    }

    companion object {
        private const val LOCATION_PERMISSIONS = 99
    }
}
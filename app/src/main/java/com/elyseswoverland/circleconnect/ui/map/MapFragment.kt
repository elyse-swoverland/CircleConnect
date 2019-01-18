package com.elyseswoverland.circleconnect.ui.map

import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elyseswoverland.circleconnect.R
import com.elyseswoverland.circleconnect.dagger.Dagger
import com.elyseswoverland.circleconnect.models.Merchant
import com.elyseswoverland.circleconnect.network.CircleConnectApiManager
import com.elyseswoverland.circleconnect.ui.util.CustomInfoWindow
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import kotlinx.android.synthetic.main.fragment_map.*
import rx.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject




class MapFragment : Fragment(), OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var mMapView: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var ctx: Context
    private var lastLocation: Location? = null
    private val groupAdapter = GroupAdapter<com.xwray.groupie.ViewHolder>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        ctx = context ?: return
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()
    }

    private fun onGetMerchantsSuccess(merchants: ArrayList<Merchant>) {
        groupAdapter.clear()
        groupAdapter.add(Section().apply {
            merchants.forEachIndexed { _, merchant ->
                val customInfoWindow = CustomInfoWindow(ctx)
                mMap.setInfoWindowAdapter(customInfoWindow)
                val m = mMap.addMarker(MarkerOptions().position(LatLng(merchant.merchLocation.latitude,
                        merchant.merchLocation.longitude)).title(merchant.merchName))
                add(MerchantItem(ctx, merchant, lastLocation, m, mMap, sliding_layout))
//                m.showInfoWindow()
            }
        })
        recyclerView.adapter = groupAdapter

    }

    private fun onGetMerchantsFailure(throwable: Throwable) {
        Log.d("TAG", "Fail :(")
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
                circleConnectApiManager.getMerchants(0, location.latitude,
                        location.longitude, 20)
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

    companion object {
        private const val LOCATION_PERMISSIONS = 99
    }
}
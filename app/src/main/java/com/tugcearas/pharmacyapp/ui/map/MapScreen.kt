package com.tugcearas.pharmacyapp.ui.map

import android.Manifest.permission.*
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.navigation.fragment.findNavController
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationServices
import com.huawei.hms.maps.HuaweiMap
import com.huawei.hms.maps.MapView
import com.huawei.hms.maps.OnMapReadyCallback
import com.huawei.hms.maps.model.BitmapDescriptorFactory
import com.huawei.hms.maps.model.LatLng
import com.huawei.hms.maps.model.Marker
import com.huawei.hms.maps.model.MarkerOptions
import com.huawei.hms.site.api.SearchResultListener
import com.huawei.hms.site.api.SearchService
import com.huawei.hms.site.api.SearchServiceFactory
import com.huawei.hms.site.api.model.*
import com.tugcearas.pharmacyapp.R
import com.tugcearas.pharmacyapp.databinding.FragmentMapScreenBinding
import com.tugcearas.pharmacyapp.util.constants.Constants
import com.tugcearas.pharmacyapp.util.extensions.toastMessageLong

class MapScreen : Fragment(), OnMapReadyCallback {

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
        private const val REQUEST_CODE_LOCATION_PERMISSION = 1
    }

    private lateinit var binding: FragmentMapScreenBinding
    private var huaweiMap: HuaweiMap? = null
    private var mMarker: Marker? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().toastMessageLong(getString(R.string.map_screen_toast_message))

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        val mMapView: MapView = binding.mapview
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        Log.i(ContentValues.TAG, "sdk >= 23 M")

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val strings = arrayOf(ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION)
            ActivityCompat.requestPermissions(
                requireActivity(),
                strings,
                REQUEST_CODE_LOCATION_PERMISSION
            )
        } else {

            getAndAddCurrentLocation()
        }

        getNearbyPlace()
    }

    @RequiresPermission(allOf = [ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE])
    override fun onMapReady(hMap: HuaweiMap?) {
        if (hMap != null) {
            huaweiMap = hMap

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                    requireContext(),
                    ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getAndAddCurrentLocation()
            }
        }

//        hMap?.isMyLocationEnabled = true  ---> adding blue circle marker in map
        hMap?.uiSettings?.isMyLocationButtonEnabled = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle: Bundle? = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }

        binding.mapview.onSaveInstanceState(mapViewBundle)
    }

    private fun getAndAddCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {

                    val currentLatLng = LatLng(location.latitude, location.longitude)

                    // remove previous marker
                    mMarker?.remove()

                    mMarker = huaweiMap?.addMarker(
                        MarkerOptions()
                            .position(currentLatLng)
                            .title("Konumunuz")
                            .snippet("Buradasınız!")
                    )

                    huaweiMap?.moveCamera(com.huawei.hms.maps.CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                } else {
                    Log.e(ContentValues.TAG, "Location is null")
                }
            }
            .addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Error getting location: ${e.message}")
            }
    }

    private fun getNearbyPlace() {
        val searchService: SearchService = SearchServiceFactory.create(requireContext(), Constants.APP_GALLERY_PROJECT_API)
        val request = NearbySearchRequest()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val coordinate = Coordinate(location.latitude, location.longitude)
                    request.location = coordinate
                    request.query = "pharmacy"
                    request.radius = 5000
                    request.hwPoiType = HwLocationType.PHARMACY
                    request.language = "en"
                    request.pageIndex = 1
                    request.pageSize = 5
                    request.strictBounds = false

                    val resultListener: SearchResultListener<NearbySearchResponse> = object : SearchResultListener<NearbySearchResponse> {
                        override fun onSearchResult(results: NearbySearchResponse?) {
                            if (results == null || results.totalCount <= 0) {
                                return
                            }
                            val sites: List<Site>? = results.sites
                            if (sites == null || sites.isEmpty()) {
                                return
                            }
                            for (site in sites) {
                                val pharmacyLatLng = LatLng(site.location.lat, site.location.lng)

                                val markerIcon = BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_icon)

                                val marker = huaweiMap?.addMarker(
                                    MarkerOptions()
                                        .position(pharmacyLatLng)
                                        .title(site.name)
                                        .snippet(site.formatAddress)
                                        .icon(markerIcon)
                                )
                                // get destination
                                marker?.tag = "https://www.petalmaps.com/routes/?saddr=${mMarker?.position?.latitude},${mMarker?.position?.longitude}&daddr=${pharmacyLatLng.latitude},${pharmacyLatLng.longitude}&type=drive&utm_source=fb"
                            }

                            // navigate to destination pharmacy
                            huaweiMap?.setOnMarkerClickListener { marker ->
                                val navigationUri = marker.tag as? String
                                if (navigationUri != null) {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(navigationUri))
                                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                                        startActivity(intent)
                                    } else {
                                        Log.e(ContentValues.TAG, "No navigation app found")
                                        // You can show a message to the user here indicating no navigation app is installed.
                                    }
                                }
                                true
                            }
                        }

                        override fun onSearchError(status: SearchStatus) {
                            Log.i("TAG", "Error : ${status.errorCode}  ${status.errorMessage}")
                        }
                    }

                    searchService.nearbySearch(request, resultListener)
                } else {
                    Log.e(ContentValues.TAG, "Location is null")
                }
            }
            .addOnFailureListener { e ->
                Log.e(ContentValues.TAG, "Error getting location: ${e.message}")
            }
    }

    override fun onStart() {
        super.onStart()
        binding.mapview.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapview.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.mapview.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapview.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapview.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapview.onLowMemory()
    }
}
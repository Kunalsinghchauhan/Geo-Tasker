package com.ksc.geotasker.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.snackbar.Snackbar
import com.ksc.geotasker.R
import com.ksc.geotasker.databinding.FragmentMapsBinding

class MapsFragment : Fragment() {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var navController: NavController
    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Places if not initialized
        if (!Places.isInitialized()) {
            val apiKey = getString(R.string.MAPS_API_KEY)
            Places.initialize(requireContext(),apiKey)
        }

        // Setup AutocompleteSupportFragment
        val autocompleteSupportFragment =
            (childFragmentManager.findFragmentById(R.id.frag_search_map) as AutocompleteSupportFragment)
                .setPlaceFields(
                    listOf(
                        Place.Field.ID,
                        Place.Field.NAME,
                        Place.Field.LAT_LNG
                    )
                )

        // PlaceSelectionListener for AutocompleteSupportFragment
        autocompleteSupportFragment.setOnPlaceSelectedListener(
            object : PlaceSelectionListener {
                override fun onError(p0: Status) {
                    Toast.makeText(requireContext(), "Some Error", Toast.LENGTH_SHORT).show()
                    Log.i("MapsFragment", "onError: $p0")
                }

                override fun onPlaceSelected(place: Place) {
                    if (place.latLng != null) {
                        val latLng = place.latLng
                        val name = place.name
                        zoomOnMap(latLng, name)

                    }
                }
            }
        )

        // Obtain SupportMapFragment and get notified when the map is ready to be used
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.frag_map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // Additional configuration on map when ready
        mapFragment?.getMapAsync {
            it.mapType = GoogleMap.MAP_TYPE_HYBRID
            Toast.makeText(requireContext(), "Satellite View", Toast.LENGTH_SHORT).show()
        }

        // Setup navigation controller and button click listener
        navController = Navigation.findNavController(view)
        binding.btnSubmitMap.setOnClickListener {
            navController.navigate(R.id.action_mapsFragment_to_addOrUpdateTaskFragment)
            Toast.makeText(requireContext(), "Location Added", Toast.LENGTH_SHORT).show()
        }
    }

    // Callback for OnMapReady
    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val maker = LatLng(28.66640281677246, 77.47942352294922)
        googleMap.addMarker(
            MarkerOptions().position(maker).title("Developer House")
                .snippet("Tere Bhai ki Location hai\n Riddhi Tower 19th Floor")
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(maker, 12f))
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.apply {
            isRotateGesturesEnabled = true
            isMyLocationButtonEnabled = true
            isZoomControlsEnabled = true
            isZoomGesturesEnabled = true
            isMapToolbarEnabled = true
            isScrollGesturesEnabled = true
        }
        // Long press listener on map
        googleMap.setOnMapLongClickListener {
            googleMap.addMarker(
                MarkerOptions().position(it).title("Location Selected")
            )?.isDraggable = true
            it.apply {
                val lat = latitude
                val lng = longitude
                Toast.makeText(
                    requireContext(),
                    "latitude: $lat\nlongitude: $lng",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Snackbar message on map loaded
        googleMap.setOnMapLoadedCallback {
            Snackbar.make(requireView(), "Long Press to Add Marker", Snackbar.LENGTH_SHORT)
                .show()
        }

        // Marker click listener
        googleMap.setOnMarkerClickListener {
            it.showInfoWindow()
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it.position, 13f))
            true
        }
    }

    // Function to zoom on a specified LatLng
    fun zoomOnMap(latLng: LatLng?, name: String?) {
        val newLatLngZoom = latLng?.let { CameraUpdateFactory.newLatLngZoom(it, 15f) }
        if (newLatLngZoom != null) {
            map.animateCamera(newLatLngZoom)
            map.addMarker(
                MarkerOptions().position(latLng).title(name)
            )
        }
    }
}

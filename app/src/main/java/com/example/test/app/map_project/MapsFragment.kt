package com.example.test.app.map_project

import android.Manifest
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.Locale


class MapsFragment : Fragment(R.layout.fragment_maps), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapsBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private var googleMap: GoogleMap? = null

    private val featurePermissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permission granted, show current location on map
            showCurrentLocationOnMap()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        binding.imgGps.setOnClickListener {
            featurePermissionRequestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {

        this.googleMap = googleMap
        googleMap.setOnMarkerClickListener(this)
        googleMap.setOnMarkerDragListener(object : GoogleMap.OnMarkerDragListener {
            override fun onMarkerDragStart(marker: Marker) {}

            override fun onMarkerDrag(marker: Marker) {}

            override fun onMarkerDragEnd(marker: Marker) {
                val latLng = marker.position
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(
                    latLng.latitude,
                    latLng.longitude,
                    1
                )

                val cityName = addresses?.firstOrNull()?.locality ?: "Unknown City"
                marker.title = cityName
                marker.showInfoWindow()
            }
        })
        } catch (e: IOException) {
            Log.e(TAG, "Unable connect to Geocoder")
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val lat = marker.position.latitude.toString()
        val lng = marker.position.longitude.toString()
        val cityName = marker.title

        val bundle = bundleOf(
            Pair("city_name", cityName),
            Pair("latitude", lat),
            Pair("longitude", lng)
        )

        findNavController().navigate(
            R.id.action_mapsFragment_to_todayFragment,
            bundle
        )

        return true
    }

    private fun showCurrentLocationOnMap() {
        googleMap?.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                currentLocation = location

                val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(
                    currentLocation.latitude,
                    currentLocation.longitude,
                    1
                )

                val cityName = addresses?.firstOrNull()?.locality ?: "Unknown City"
                val markerOptions = MarkerOptions().position(latLng).title(cityName).draggable(true)
                googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 7f))
                googleMap?.clear()
                googleMap?.addMarker(markerOptions)?.tag = cityName
            }
        }
    }
}
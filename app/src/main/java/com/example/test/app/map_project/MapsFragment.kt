package com.example.test.app.map_project

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MapsFragment : Fragment(R.layout.fragment_maps), GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapsBinding
    private val viewModel: MapsViewModel by viewModels()
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val permissionCode = 101
    private lateinit var currentLocation: Location

    private val featurePermissionRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
        ::onGotPermissionResultOnFeatures
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsBinding.bind(view)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val callback = OnMapReadyCallback { googleMap ->
        binding.imgGps.setOnClickListener {
            featurePermissionRequestLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            val getLocation = fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = location
                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses = geocoder.getFromLocation(
                        currentLocation.latitude,
                        currentLocation.longitude,
                        1
                    )
                    val cityName = addresses?.get(0)?.locality
                    val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                    val markerOptions =
                        MarkerOptions().position(latLng).title(cityName).draggable(true)

                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                    val marker = googleMap.addMarker(markerOptions)

                    // Set a drag listener on the marker
                    googleMap.setOnMarkerDragListener(this)

                    // Set a click listener on the marker
                    googleMap.setOnMarkerClickListener(this)


                }
            }
        }
    }


    private fun onGotPermissionResultOnFeatures(granted: Boolean) {
        when (granted) {
            true -> Toast.makeText(
                requireActivity(),
                R.string.location_permission_granted,
                Toast.LENGTH_SHORT
            ).show()
            else -> Toast.makeText(
                requireActivity(),
                "Permission denied",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // get the title of the clicked marker
        val title = marker.title

        // create the bundle with the new argument value
        val bundle = bundleOf("city_name" to title)

        // navigate to the detail fragment with the new argument
        findNavController().navigate(R.id.action_mapsFragment_to_todayFragment, bundle)

        return true
    }
}
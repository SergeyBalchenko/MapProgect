package com.example.test.app.map_project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.api.view_models.WeatherViewModel
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentTodayBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

 const val TAG = "My Tag"
class TodayFragment : Fragment(R.layout.fragment_today) {
    private lateinit var binding: FragmentTodayBinding
    private lateinit var viewModelWeather: WeatherViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayBinding.bind(view)

        val repository = Repository()
        val viewModelFactory = WeatherViewModelFactory(repository)
        viewModelWeather = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
        val latitude = arguments?.getString("latitude") ?: "Unknown latitude"
        val longitude = arguments?.getString("longitude") ?: "Unknown longitude"
        viewModelWeather.getWeather(latitude,longitude)
        viewModelWeather.myResponse.observe(viewLifecycleOwner, Observer { response ->
            binding.tvCity.text = response.location.name.toString()
            binding.tvTemp.text = response.current.temp_c.toString()
            binding.tvUfo.text = response.current.uv.toString()
            binding.tvGustKph.text = response.current.gust_kph.toString()
        })

        // Initialize the AutocompleteSupportFragment
        val autocompleteFragment = AutocompleteSupportFragment()
        childFragmentManager.beginTransaction()
            .replace(R.id.autocomplete_fragment, autocompleteFragment)
            .commit()


        val cityName = arguments?.getString("city_name") ?: "Unknown City"
        binding.tvCityFromMap.text = latitude.toString()

        (activity as AppCompatActivity).supportActionBar?.title = cityName.toString()

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }

            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }
        })
    }
}
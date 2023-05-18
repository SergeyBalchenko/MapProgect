package com.example.test.app.map_project

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.api.view_models.WeatherViewModel
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentTodayBinding
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

const val TAG = "My Tag"
class TodayFragment : Fragment(R.layout.fragment_today) {
    private lateinit var binding: FragmentTodayBinding
    private lateinit var viewModelWeather: WeatherViewModel
    private lateinit var autocompleteFragment: AutocompleteSupportFragment // Declare the AutocompleteSupportFragment variable

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayBinding.bind(view)

        // Get latitude and longitude from arguments
        val latitude = arguments?.getString("latitude") ?: "Unknown latitude"
        val longitude = arguments?.getString("longitude") ?: "Unknown longitude"

        // Initialize the ViewModel
        val repository = Repository()
        val viewModelFactory = WeatherViewModelFactory(repository)
        viewModelWeather = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)

        // Send request to get weather data
        viewModelWeather.getWeather(latitude, longitude)

        // Observe the response from the ViewModel
        viewModelWeather.myForecastResponse.observe(viewLifecycleOwner) { response ->
            binding.tvCity.text = response.location.name
            binding.tvTemp.text = response.current.temp_c.toString()
            binding.tvUfo.text = response.current.uv.toString()
            binding.tvGustKph.text = response.current.gust_kph.toString()
        }

        viewModelWeather.events.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Get city name from arguments and set it as the title of the toolbar
        val cityName = arguments?.getString("city_name") ?: "Unknown City"
        (activity as AppCompatActivity).supportActionBar?.title = cityName

        // Initialize the AutocompleteSupportFragment
        autocompleteFragment = AutocompleteSupportFragment()

        // Replace the placeholder layout with the AutocompleteSupportFragment
        childFragmentManager.beginTransaction()
            .replace(R.id.autocomplete_fragment, autocompleteFragment)
            .commit()

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

//        findNavController().navigate(R.id.action_todayFragment_to_weekFragment, bundle)

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onError(status: Status) {
                Log.i(TAG, "An error occurred: $status")
            }

            override fun onPlaceSelected(place: Place) {
                // Set the title of the toolbar to the selected place name
                (activity as AppCompatActivity).supportActionBar?.title = place.name?.toString()

                // Get the latitude and longitude of the selected place
                val latLng = place.latLng

                // Send request to get weather data for the selected place
                viewModelWeather.getWeather(latLng?.latitude.toString(), latLng?.longitude.toString())

                val bundle = Bundle()
                bundle.putString("city_name", cityName)
                bundle.putString("latitude", latLng.latitude.toString())
                bundle.putString("longitude", latLng.longitude.toString())

                val fragmentWeek = WeekFragment()

                fragmentWeek.arguments = bundle
            }
        })
    }
}
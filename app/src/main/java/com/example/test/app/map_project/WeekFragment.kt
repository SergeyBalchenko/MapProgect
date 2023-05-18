package com.example.test.app.map_project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.app.map_project.api.RetrofitInstance
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.api.view_models.WeatherViewModel
import com.example.test.app.map_project.util.Constants
import com.example.test.app.map_project.util.WeekWeatherViewModel
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentWeekBinding
import retrofit2.HttpException
import java.io.IOException


class WeekFragment : Fragment(R.layout.fragment_week) {
    lateinit var binding: FragmentWeekBinding

    lateinit var weatherWeekViewModel: WeekWeatherViewModel
    lateinit var weatherWeekAdapter: WeatherWeekAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeekBinding.bind(view)
        val bundle = arguments
        // Get latitude and longitude from arguments
        val latitude = bundle?.getString("latitude")
        val longitude = bundle?.getString("longitude")
        if (longitude != null) {
            if (latitude != null) {
                setupRecyclerView(latitude, longitude)
            }
        }
        // Initialize the ViewModel
        val repository = Repository()
        val viewModelFactory = WeatherWeekViewModelFactory(repository)
        weatherWeekViewModel = ViewModelProvider(this, viewModelFactory).get(WeekWeatherViewModel::class.java)

        // Send request to get weather data
        if (latitude != null) {
            if (longitude != null) {
                weatherWeekViewModel.getWeather(latitude, longitude)
            }
        }

        // Observe the response from the ViewModel
        weatherWeekViewModel.myForecastResponse.observe(viewLifecycleOwner) { response ->
            weatherWeekAdapter.weather = response.forecast.forecastday
        }
        // Get city name from arguments and set it as the title of the toolbar
        val cityName = bundle?.getString("city_name") ?: "Unknown City"
        (activity as AppCompatActivity).supportActionBar?.title = cityName

    }

    private  fun setupRecyclerView(latitude: String, longitude: String) = binding.rvWeather.apply {
        weatherWeekAdapter = WeatherWeekAdapter()
        adapter = weatherWeekAdapter
        layoutManager = LinearLayoutManager(requireActivity())
    }
}
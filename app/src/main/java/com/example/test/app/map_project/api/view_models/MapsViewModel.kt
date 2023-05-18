package com.example.test.app.map_project

import android.location.Geocoder
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.Locale

class MapsViewModel(private val repository: MapsRepository) : ViewModel() {

    private val _cityName = MutableLiveData<String>()
    val cityName: LiveData<String>
        get() = _cityName

    private val _selectedCityName = MutableLiveData<String>()
    val selectedCityName: LiveData<String>
        get() = _selectedCityName

    fun getCurrentCityName(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val cityName = repository.getCityName(location.latitude, location.longitude)
                _cityName.postValue(cityName)
            } catch (e: IOException) {
                // Handle the error
            }
        }
    }

    fun setSelectedCityName(cityName: String) {
        _selectedCityName.postValue(cityName)
    }
}

class MapsRepository(private val geocoder: Geocoder) {

    @Throws(IOException::class)
    suspend fun getCityName(latitude: Double, longitude: Double): String {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (addresses != null) {
            if (addresses.isEmpty()) {
                throw IOException("No addresses found for location")
            }
        }
        return addresses?.get(0)?.locality ?: ""
    }
}
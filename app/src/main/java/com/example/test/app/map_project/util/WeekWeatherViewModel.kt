package com.example.test.app.map_project.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.model.WeatherData
import kotlinx.coroutines.launch

class WeekWeatherViewModel(private val repository: Repository) : ViewModel() {

    val myForecastResponse: MutableLiveData<WeatherData> = MutableLiveData()

    private val _events = MutableLiveData<String>()
    val events: LiveData<String> = _events

    fun getWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            repository.getWeather(Constants.API_KEY, latitude, longitude)
                .fold(
                    onSuccess = {
                        myForecastResponse.postValue(it)
                    },
                    onFailure = {
                        _events.postValue(it.toString())
                    }
                )
        }
    }
}
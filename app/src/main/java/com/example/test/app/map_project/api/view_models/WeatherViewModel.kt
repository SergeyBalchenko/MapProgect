package com.example.test.app.map_project.api.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.model.WeatherData
import com.example.test.app.map_project.util.Constants.Companion.API_KEY
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

const val TAG = "WeatherViewModel"

class WeatherViewModel(private val repository: Repository) : ViewModel() {

    val myForecastResponse: MutableLiveData<WeatherData> = MutableLiveData()

    private val _events = MutableLiveData<String>()
    val events: LiveData<String> = _events

    fun getWeather(latitude: String, longitude: String) {
        viewModelScope.launch {
            repository.getWeather(API_KEY, latitude, longitude)
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
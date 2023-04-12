package com.example.test.app.map_project.api.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.model.Current
import com.example.test.app.map_project.model.WeatherData
import com.example.test.app.map_project.util.Constants.Companion.API_KEY
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
const val  TAG = "WeatherViewModel"
class WeatherViewModel(private val repository: Repository): ViewModel() {

    val myResponse: MutableLiveData<WeatherData> = MutableLiveData()

    fun getWeather() {
        viewModelScope.launch {
            val response: WeatherData =
                repository.getWeather()
                myResponse.value = response
            try {
            } catch (e: IOException) {
                Log.e(TAG,"IOEXEPTION you might not have internet connection")
            } catch (e: HttpException) {
                Log.e(TAG,"HttpException, unexpected response")
            }
        }
    }
}
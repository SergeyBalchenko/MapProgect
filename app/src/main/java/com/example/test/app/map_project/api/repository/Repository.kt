package com.example.test.app.map_project.api.repository

import com.example.test.app.map_project.api.RetrofitInstance
import com.example.test.app.map_project.model.WeatherData
import com.example.test.app.map_project.util.Constants.Companion.API_KEY

class Repository {

    suspend fun getWeather(API_KEY: String, latitude: String, longitude: String): WeatherData {
        return RetrofitInstance.api.getWeather(API_KEY,"$latitude,$longitude")
    }
}
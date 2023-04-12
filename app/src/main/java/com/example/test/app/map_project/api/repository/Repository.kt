package com.example.test.app.map_project.api.repository

import com.example.test.app.map_project.api.RetrofitInstance
import com.example.test.app.map_project.model.Current
import com.example.test.app.map_project.model.WeatherData
import com.example.test.app.map_project.util.Constants.Companion.API_KEY
import retrofit2.Response

class Repository {

    suspend fun getWeather() : WeatherData{
        return RetrofitInstance.api.getWeather(API_KEY, "48.8567,2.3508")
    }
}
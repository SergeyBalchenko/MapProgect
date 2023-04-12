package com.example.test.app.map_project.api

import com.example.test.app.map_project.model.Current
import com.example.test.app.map_project.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("q") lat: String,
//        @Query("lon") lon: String,
        @Query("aqi") aqi: String = "no"
    ): WeatherData
}

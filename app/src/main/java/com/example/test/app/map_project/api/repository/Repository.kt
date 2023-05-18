package com.example.test.app.map_project.api.repository

import com.example.test.app.map_project.api.RetrofitInstance
import com.example.test.app.map_project.model.WeatherData
import com.example.test.app.map_project.util.Constants.Companion.API_KEY
import java.lang.Exception

class Repository {

    suspend fun getWeather(API_KEY: String, latitude: String, longitude: String): Result<WeatherData> {
        try {
            val response = RetrofitInstance.api.getWeather(
                key = API_KEY,
                lat = "$latitude,$longitude",
                days = 5
            )
            if (response.isSuccessful) {
                val data = response.body()!!
                return Result.success(data)
            } else {
                response.code()
                return Result.failure(
                    MyException()
                )
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}
class MyException: Exception()
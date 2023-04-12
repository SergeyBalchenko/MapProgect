package com.example.test.app.map_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.api.view_models.WeatherViewModel

class WeatherViewModelFactory(
    private val repository: Repository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return WeatherViewModel(repository) as T
        }
}
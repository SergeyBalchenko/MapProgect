package com.example.test.app.map_project

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.api.view_models.WeatherViewModel
import com.example.test.app.map_project.util.WeekWeatherViewModel

class WeatherWeekViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeekWeatherViewModel(repository) as T
    }
}

package com.example.test.app.map_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.test.app.map_project.api.repository.Repository
import com.example.test.app.map_project.api.view_models.WeatherViewModel
import com.example.test.app.map_project.util.Constants.Companion.API_KEY_GOOGLE_SERVICES
import com.example.test.app.mapprogect.R
import com.google.android.libraries.places.api.Places
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }
//        val repository = Repository()
//        val viewModelFactory = WeatherViewModelFactory(repository)
//        viewModelWeather = ViewModelProvider(this, viewModelFactory).get(WeatherViewModel::class.java)
//        viewModelWeather.getWeather()
//        viewModelWeather.myResponse.observe(this, Observer { response ->
//            Log.d("Response", response.condition.toString())
//            Log.d("Response", response.gust_kph.toString())
//            Log.d("Response", response.is_day.toString())
//            Log.d("Response", response.last_updated.toString())
//            Log.d("Response", response.last_updated_epoch.toString())
//            Log.d("Response", response.temp_c.toString())
//            Log.d("Response", response.uv.toString())
//        })
        Places.initialize(applicationContext,API_KEY_GOOGLE_SERVICES)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.mapsFragment, R.id.todayFragment, R.id.weekFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

    }
}
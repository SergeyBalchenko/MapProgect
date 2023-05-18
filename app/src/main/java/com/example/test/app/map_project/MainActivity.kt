package com.example.test.app.map_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
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
        Places.initialize(applicationContext,API_KEY_GOOGLE_SERVICES)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(R.id.fragmentContainerView)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.mapsFragment, R.id.todayFragment, R.id.weekFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.todayFragment -> {
                    navController.navigate(R.id.todayFragment)
                    true
                }
                R.id.mapsFragment -> {
                    navController.navigate(R.id.mapsFragment)
                    true
                }
                R.id.weekFragment -> {
                    navController.navigate(R.id.weekFragment)
                    true
                }
                else -> false
            }
        }

    }
}
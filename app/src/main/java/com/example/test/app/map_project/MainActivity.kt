package com.example.test.app.map_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition{
                viewModel.isLoading.value
            }
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(TodayFragment())

        binding.bottomNav.setOnItemSelectedListener {
            when(it.itemId){
                R.id.btn_map -> replaceFragment(MapsFragment())
                R.id.btn_today -> replaceFragment(TodayFragment())
                R.id.btn_week -> replaceFragment(WeekFragment())

                else -> {

                }
            }
            true
        }

    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_container, fragment)
        fragmentTransaction.commit()
    }
}
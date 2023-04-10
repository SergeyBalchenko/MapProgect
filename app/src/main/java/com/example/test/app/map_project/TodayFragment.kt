package com.example.test.app.map_project

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentTodayBinding


class TodayFragment : Fragment(R.layout.fragment_today) {
    private lateinit var binding: FragmentTodayBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTodayBinding.bind(view)

        binding.tvCityName.text = arguments?.getString("city_name")
    }
}
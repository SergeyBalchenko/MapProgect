package com.example.test.app.map_project

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.test.app.mapprogect.R
import com.example.test.app.mapprogect.databinding.FragmentWeekBinding


class WeekFragment : Fragment(R.layout.fragment_week) {
    lateinit var binding: FragmentWeekBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeekBinding.bind(view)
    }
}
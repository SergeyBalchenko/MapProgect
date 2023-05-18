package com.example.test.app.map_project

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.test.app.map_project.model.Forecastday
import com.example.test.app.mapprogect.databinding.ItemWeatherDailyBinding

class WeatherWeekAdapter : RecyclerView.Adapter<WeatherWeekAdapter.WeatherWeekViewHolder>() {

    inner class WeatherWeekViewHolder(val binding: ItemWeatherDailyBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Forecastday>() {
        override fun areItemsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Forecastday, newItem: Forecastday): Boolean {
            return  oldItem == newItem
        }
    }

        private  val differ = AsyncListDiffer(this, diffCallback)
        var weather: List<Forecastday>
            get() = differ.currentList
            set(value) {differ.submitList(value) }

    override fun getItemCount() = weather.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherWeekViewHolder {
        return  WeatherWeekViewHolder(ItemWeatherDailyBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: WeatherWeekViewHolder, position: Int) {
        holder.binding.apply {
            val weather = weather[position]
            tvDay.text = weather.date
            tvTemp.text = weather.day.avgtemp_c.toString()
            tvAv.text = weather.day.avghumidity.toString()
            tvWindForce.text = weather.day.maxwind_kph.toString()
        }
    }
}
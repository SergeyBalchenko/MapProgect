package com.example.test.app.map_project.model

data class Current(
    val condition: Condition,
    val gust_kph: Double,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val temp_c: Double,
    val uv: Double
)
package com.example.test.app.map_project.model

data class Hour(
    val condition: ConditionX,
    val temp_c: Double,
    val time: String,
    val uv: Double,
    val wind_kph: Double
)
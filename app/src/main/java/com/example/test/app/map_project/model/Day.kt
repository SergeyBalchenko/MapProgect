package com.example.test.app.map_project.model

data class Day(
    val avghumidity: Double,
    val avgtemp_c: Double,
    val avgvis_km: Double,
    val condition: ConditionX,
    val maxtemp_c: Double,
    val maxwind_kph: Double,
    val mintemp_c: Double,
    val totalprecip_in: Double,
    val totalsnow_cm: Double,
    val uv: Double
)
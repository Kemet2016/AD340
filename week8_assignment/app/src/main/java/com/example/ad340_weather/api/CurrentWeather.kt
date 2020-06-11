package com.example.ad340_weather.api

import com.squareup.moshi.Json

data class Forecast(val temp: Float)
data class Coordinates(val lat: Float, val lon: Float)


/**
 * Api response for OpenWeatherMap's /weather endpoint
 */
data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name= "main") val forecast: Forecast
)
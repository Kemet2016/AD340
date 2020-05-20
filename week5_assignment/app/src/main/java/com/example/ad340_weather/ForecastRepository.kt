package com.example.ad340_weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository
{

    private val _weeklyForecast = MutableLiveData<List<DailyForecast>>()
    val weeklyForecast : LiveData<List<DailyForecast>> = _weeklyForecast

    fun loadForecast(zipcode: String)
    {
        val randomValues = List(7) { Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map {temp ->
            DailyForecast(temp, getTempDescription(temp))
        }

        _weeklyForecast.setValue(forecastItems)
    }

    private fun getTempDescription(temp: Float) : String
    {

        return when(temp)
        {
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below zero doesn't make sense"
            in 0f.rangeTo(32f) -> "It is freezen"
            in 32f.rangeTo(55f) -> "It is cold"
            in 55f.rangeTo(65f) -> "It is getting better"
            in 65f.rangeTo(80f) -> "It is nice"
            in 80f.rangeTo(90f) -> "It is too hot"
            in 90f.rangeTo(100f) -> "We are melting"
            in 100f.rangeTo(Float.MAX_VALUE) -> "It is burning"
            else -> "Does not compute"
        }

    }

}
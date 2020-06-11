package com.example.ad340_weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ad340_weather.api.CurrentWeather
import com.example.ad340_weather.api.WeeklyForecast
import com.example.ad340_weather.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*
//import javax.security.auth.callback.Callback
import kotlin.random.Random

class ForecastRepository
{

    private val weatherService = createOpenWeatherMapService()
    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather: LiveData<CurrentWeather> = _currentWeather

    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast : LiveData<WeeklyForecast> = _weeklyForecast


    fun loadCurrentForecast(zipcode: String) {
        val call = weatherService.currentWeather(zipcode, BuildConfig.OPEN_WEATHER_MAP_API_KEY, "imperial")
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading current weather", t)
            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if(weatherResponse != null) {
                    _currentWeather.value = weatherResponse
                }

            }

        })
    }

    fun loadWeeklyForecast(zipcode: String)
    {
        val call = weatherService.currentWeather(zipcode, BuildConfig.OPEN_WEATHER_MAP_API_KEY, "imperial")
        call.enqueue(object : Callback<CurrentWeather>{
            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e(ForecastRepository::class.java.simpleName, "error loading location for weekly forecast", t)
            }

            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                val weatherResponse = response.body()
                if(weatherResponse != null) {
                    // load 7 days forecast
                    val forecastCall = createOpenWeatherMapService().sevenDayForecast(
                        lat = weatherResponse.coord.lat,
                        lon = weatherResponse.coord.lon,
                        exclude = "current, minutely, hourly",
                        units = "imperial",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    forecastCall.enqueue(object: Callback<WeeklyForecast> {
                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e(ForecastRepository::class.java.simpleName, "error loading weekly forecast")
                        }

                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weelyForecastResponse = response.body()
                            if(weelyForecastResponse != null) {
                                _weeklyForecast.value = weelyForecastResponse
                            }
                        }

                    })
                }

            }

        })
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
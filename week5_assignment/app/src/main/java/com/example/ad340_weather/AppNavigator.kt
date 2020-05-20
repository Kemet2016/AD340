package com.example.ad340_weather

interface AppNavigator
{

    fun navigateToCurrentForecast(zipcode: String)
    fun navigateToLocationEntry()
}
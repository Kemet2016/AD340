package com.example.ad340_weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initiate the components
        val zipCode: EditText = findViewById(R.id.ZipCode)
        val button: Button = findViewById(R.id.Button)

// add clickLestner to the button
        button.setOnClickListener {
            val zipcode : String = zipCode.text.toString()

            if( zipcode.length != 5)
            {
                Toast.makeText(this, R.string.zipcode_error, Toast.LENGTH_SHORT).show()
            } else
            {
                forecastRepository.loadForecast(zipcode)
            }

        }

        val foreCastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager =  LinearLayoutManager(this)

        val dailyForecastAdapter = DailyForecastAdapter() {forecastItem ->
            val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
        forecastList.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<List<DailyForecast>>{forecastItems ->
            // update our list adapter
           dailyForecastAdapter.submitList(forecastItems)
        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)



    }
}

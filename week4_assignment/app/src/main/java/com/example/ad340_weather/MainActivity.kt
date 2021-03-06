package com.example.ad340_weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ad340_weather.details.ForecastDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val forecastRepository = ForecastRepository()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

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

        val forecastList: RecyclerView = findViewById(R.id.forecastList)
        forecastList.layoutManager =  LinearLayoutManager(this)

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) {forecast ->
                showForecastDetails(forecast)
        }
        forecastList.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<List<DailyForecast>>{forecastItems ->
            // update our list adapter
           dailyForecastAdapter.submitList(forecastItems)
        }
        forecastRepository.weeklyForecast.observe(this, weeklyForecastObserver)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle item selection
        return when (item.itemId)
        {
            R.id.tempDisplaySettings -> {
                showTempDisplaySettingDialog(this, tempDisplaySettingManager)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }


    private fun showForecastDetails(forecast: DailyForecast)
    {
        val forecastDetailsIntent = Intent(this, ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)
    }

}

package com.example.hello_ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipCode : EditText = findViewById(R.id.ZipCode)
        val enterButton : Button = findViewById(R.id.Enter)

        enterButton.setOnClickListener {
val zipCode: String = zipCode.text.toString()
            if(zipCode.length != 5)
            {
                Toast.makeText(this, R.string.zipcode_error, Toast.LENGTH_SHORT).show()

            }
            else
            {
                Toast.makeText(this, zipCode, Toast.LENGTH_SHORT).show()
            }

        }
    }
}

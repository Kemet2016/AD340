package com.example.ad340_weather.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.ad340_weather.AppNavigator

import com.example.ad340_weather.R
import java.lang.Appendable

/**
 * A simple [Fragment] subclass.
 */
class LocationEntryFragment : Fragment() {

    private lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        // initiate the components
        val zipCode: EditText = view.findViewById(R.id.ZipCode)
        val button: Button = view.findViewById(R.id.Button)

        // add clickLestner to the button
        button.setOnClickListener {
            val zipcode : String = zipCode.text.toString()

            if( zipcode.length != 5)
            {
                Toast.makeText(requireContext(), R.string.zipcode_error, Toast.LENGTH_SHORT).show()
            } else
            {
                appNavigator.navigateToCurrentForecast(zipcode)

            }

        }


        return view
    }

}

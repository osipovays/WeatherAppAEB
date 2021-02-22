package com.osipsen.weather


import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class WeatherCity : AppCompatActivity() {

    var CITY: String = "-city-"
    var CITYD: String = "-temperature-"
    val API: String = "e9f27d7a3f85307ad5fe961b8d4509ff"

    override fun onCreate(savedInstanceState: Bundle?) {
        CITY = intent.getStringExtra("CITYNAMESQL").toString()
        CITYD = intent.getStringExtra("CITYD").toString()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_city)
        weatherTask().execute()

        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener{
            onBackPressed()
        }


    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE
            findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.GONE
            findViewById<TextView>(R.id.errorText).visibility = View.GONE
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/forecast?q=$CITY&lang=ru&units=metric&appid=$API&cnt=28").readText(
                        Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                val jsonObj = JSONObject(result)

                val list = jsonObj.getJSONArray("list")
                val city = jsonObj.getJSONObject("city")

                val address = city.getString("name")

                val indexWeath = list.getJSONObject(2);
                val main = indexWeath.getJSONObject("main")
                val weather = indexWeath.getJSONArray("weather").getJSONObject(0)
                val tempMin = "Мин. температура: " + main.getString("temp_min")+"°C"
                val tempMax = "Макс. температура: " + main.getString("temp_max")+"°C"
                val weatherDescription = weather.getString("description")

                val indexWeath1 = list.getJSONObject(10);
                val main1 = indexWeath1.getJSONObject("main")
                val temp1 = main1.getString("temp")+"°C"

                val indexWeath2 = list.getJSONObject(18);
                val main2 = indexWeath2.getJSONObject("main")
                val temp2 = main2.getString("temp")+"°C"
                val date = indexWeath2.getString("dt_txt")

                val indexWeath3 = list.getJSONObject(26);
                val main3 = indexWeath3.getJSONObject("main")
                val temp3 = main3.getString("temp")+"°C"
                val date2 = indexWeath3.getString("dt_txt")

                findViewById<TextView>(R.id.address).text = address
                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = CITYD
                findViewById<TextView>(R.id.temp_min).text = tempMin
                findViewById<TextView>(R.id.temp_max).text = tempMax
                findViewById<TextView>(R.id.tomorrow).text = temp1
                findViewById<TextView>(R.id.tomorrow1).text = temp2
                findViewById<TextView>(R.id.tomorrow11).text = date
                findViewById<TextView>(R.id.tomorrow2).text = temp3
                findViewById<TextView>(R.id.tomorrow21).text = date2

                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<RelativeLayout>(R.id.mainContainer).visibility = View.VISIBLE

            } catch (e: Exception) {
                findViewById<ProgressBar>(R.id.loader).visibility = View.GONE
                findViewById<TextView>(R.id.errorText).visibility = View.VISIBLE
            }

        }
    }

    override fun onStart() {
        super.onStart()
    }
    override fun onResume() {
        super.onResume()
    }
    override fun onPostResume() {
        super.onPostResume()
    }
    override fun onPause() {
        super.onPause()
    }
    override fun onStop() {
        super.onStop()
    }
    override fun onRestart() {
        super.onRestart()
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}
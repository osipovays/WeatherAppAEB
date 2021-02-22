package com.osipsen.weather
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.net.URL


class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var CITY = "-city-"
    private var city = arrayOf("-temperature-", "-temperature-", "-temperature-")
    val API: String = "e9f27d7a3f85307ad5fe961b8d4509ff"
    private var titles = arrayOf("Якутск", "Москва", "Санкт-Петербург")
    private var CITYS = arrayOf("yakutsk,ru", "moscow,ru", "Saint%20Petersburg,ru")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        CITY = CITYS[position]
        city[position] = WeatherTask().execute().get()
        holder.itemTitle.text = titles[position]
        holder.itemDetail.text = city[position]
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var itemTitle: TextView
        var itemDetail: TextView

        init{
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetail = itemView.findViewById(R.id.item_detail)

           itemView.setOnClickListener{
               val intent = Intent(itemView.context, WeatherCity::class.java)
               intent.putExtra("CITYNAMESQL",CITYS[position])
               intent.putExtra("CITYD",city[position])
               itemView.context.startActivities(arrayOf(intent));
           }
        }
    }

    inner class WeatherTask() : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg params: String?): String? {
            var city2 = "-temperature-"
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                        Charsets.UTF_8
                )
                val jsonObj = JSONObject(response)
                val main = jsonObj.getJSONObject("main")
                val temp = main.getString("temp")+"°C"
                city2 = temp
            }catch (e: Exception){
                city2 = "-temperature-"
            }
            return city2
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

        }
    }

}
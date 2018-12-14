package com.algonquincollege.jalo0011.androidfinal

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class FoodActivity : Activity() {

    val apiKey = "7aa8ca6762be7b69b136c1aebb30d5d7"
    val apiID = "dc2728fc"

    lateinit var search_btn : ImageButton
    lateinit var field : EditText
    lateinit var search_input : String
    lateinit var help_btn : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        search_btn = findViewById(R.id.search_btn)
        field = findViewById(R.id.search_field)
        help_btn = findViewById(R.id.floatingActionButton)

        search_btn.setOnClickListener {
            search_input = field.getText().toString()
            var yo = "Hey"
            Log.i("LOGGING","CLICK CLICK CLICK")
            Log.i("search_input","User searched " + search_input)
            var query = FoodQuery()
            query.execute()

        }

        help_btn.setOnClickListener{
            Toast.makeText(this, "Author: Mike \n Version: 2.0 \n Enter your desired search. Add your favorites by clicking on the heart icon. View favorites list by clicking on the button at the bottom of the page.", Toast.LENGTH_SHORT).show()
        }
    }

    inner class FoodQuery : AsyncTask<String, Integer, String>(){

        var progress = 0
        var foodImage : Bitmap? = null

        override fun doInBackground(vararg params: String?): String {

            Log.i("Query","Got Here!")
            var data : String
            try {
                Log.i("trying","try")
                val url = URL("https://api.edamam.com/api/food-database/parser?app_id=e5bc806d&app_key=5f7521ffeefe491b936cea6271e13d3d&ingr=" + search_input)
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connect()
                data = urlConnection.inputStream.use { it.reader().use {reader -> reader.readText()   } }


                return data

            }
            catch(e: Exception){
                Log.i("Error","Issue: ${e}")
            }

            return "Done"

        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }

        private fun handleJson(jsonString: String?){

            var jsonArray = JSONArray(jsonString)

            var x = 0
            while(x < jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(x)
//                Log.i("food id","id: ${jsonObject.get("foodId")}")
            }

        }



//        override fun onProgressUpdate(vararg values: Integer?) {
//            //textView.setText
//            progressBar.setProgress(progress)
//            currTemp.setText("Current temperature: $tempValue")
//            currMax.setText("Max temp: $tempMax")
//            currMin.setText("Min temp: $tempMin")
//            windSpe.setText("Windspeed: $windValue")
//
//        }
//
//        override fun onPostExecute(result: String?) {
//            currWeather.setImageBitmap(weatherImage)
//            progressBar.visibility = View.INVISIBLE
//        }
//
//        fun getImage(url: URL): Bitmap? {
//            var connection: HttpURLConnection? = null
//            try {
//                connection = url.openConnection() as HttpURLConnection
//                connection.connect()
//                val responseCode = connection.responseCode
//                return if (responseCode == 200) {
//                    BitmapFactory.decodeStream(connection.inputStream)
//                } else
//                    null
//            } catch (e: Exception) {
//                return null
//            } finally {
//                connection?.disconnect()
//            }
//        }
//
//        fun getImage(urlString: String): Bitmap? {
//            try {
//                val url = URL(urlString)
//                return getImage(url)
//            } catch (e: MalformedURLException) {
//                return null
//            }
//
//        }

        fun fileExistance(fname : String):Boolean{
            val file = getBaseContext().getFileStreamPath(fname)
            return file.exists()   }


    }


}

package com.algonquincollege.jalo0011.androidfinal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL

class CBCInfoActivity : AppCompatActivity() {

    val ACTIVITY_NAME = "CBCInfoActivity"

    var title : String =""
    var description : String=""
    var link : String=""
    var pubDate : String=""
    var author : String=""
    var category : String=""

    lateinit var imageV : ImageView
    lateinit var titleV : TextView
    lateinit var categoryV : TextView
    lateinit var pubDateV : TextView
    lateinit var authorV : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cbc_tiles)

        titleV = findViewById<TextView>(R.id.title)
        pubDateV = findViewById<TextView>(R.id.pubDate)
        categoryV = findViewById<TextView>(R.id.category)


        var progress = findViewById<ProgressBar>(R.id.progress)

        progress.visibility = View.VISIBLE
        var myQuery = MovieQuery()
        myQuery.execute("") // runs query
    }

    inner class MovieQuery : AsyncTask<String, Integer, String>() {

        var progress = 0
        override fun doInBackground(vararg p0: String?): String {
            try {

                val url = URL("https://www.cbc.ca/cmlink/rss-world")
                val urlConnection = url.openConnection() as HttpURLConnection
                val stream = urlConnection.getInputStream()

                val factory = XmlPullParserFactory.newInstance()
                factory.setNamespaceAware(false)
                val xpp = factory.newPullParser()
                xpp.setInput( stream, "UTF-8") //set the input stream

                while(xpp.eventType != XmlPullParser.END_DOCUMENT){

                    when(xpp.eventType){
                        XmlPullParser.START_TAG -> {
                            if(xpp.name.equals("item")) {

                                title = xpp.getAttributeValue(null, "title")
                                progress += 15
                                description = xpp.getAttributeValue(null, "description")
                                progress += 15
                                link = xpp.getAttributeValue(null, "link")
                                progress += 15
                                pubDate = xpp.getAttributeValue(null, "pubDate")
                                progress += 15
                                author = xpp.getAttributeValue(null, "author")
                                progress += 15
                                category = xpp.getAttributeValue(null, "category")
                                progress += 25
                            }

                            publishProgress() //causes android to call onProgressUpdate when GUI is ready

                        }
                        XmlPullParser.TEXT -> {   }
                    }

                    xpp.next() //moves to the next tag
                }
            }
            catch (e: Exception) {
                var message = e.message
            }

            return "Done"
        }


        override fun onProgressUpdate(vararg values: Integer?) { // update GUI
            titleV.setText("Current Temp: $title")
            pubDateV.setText("Minimum Temp: $pubDate")
            categoryV.setText("Wind Speed: $category")


        }


        fun getImage(url: URL): Bitmap? {
            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val responseCode = connection.responseCode
                return if (responseCode == 200) {
                    BitmapFactory.decodeStream(connection.inputStream)
                } else
                    null
            } catch (e: Exception) {
                return null
            } finally {
                connection?.disconnect()
            }
        }
    }
}

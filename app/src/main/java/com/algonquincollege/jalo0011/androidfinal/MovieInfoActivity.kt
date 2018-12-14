package com.algonquincollege.jalo0011.androidfinal

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_movie.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL

class MovieInfoActivity : Activity() {
    val ACTIVITY_NAME = "MovieInfoActivity"

    var title : String = ""
    var year: String = ""
    var rating: String = ""
    var runtime: String = ""
    var actor: String = ""
    var plot: String = ""
    var posterIcon: String = ""
    var movieImage : Bitmap? = null

    lateinit var imageCurrMoviePoster : ImageView
    lateinit var currMovieTitle : TextView
    lateinit var currMovieYear : TextView
    lateinit var currMovieRating : TextView
    lateinit var currMovieRuntime : TextView
    lateinit var currMovieActor : TextView
    lateinit var currMoviePlot : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_info)
        imageCurrMoviePoster = findViewById<ImageView>(R.id.moviePoster)
        currMovieTitle = findViewById<TextView>(R.id.movieTitle)
        currMovieYear = findViewById<TextView>(R.id.movieYear)
        currMovieRating = findViewById<TextView>(R.id.movieRating)
        currMovieRuntime = findViewById<TextView>(R.id.movieRuntime)
        currMovieActor = findViewById<TextView>(R.id.movieActors)
        currMoviePlot = findViewById<TextView>(R.id.moviePlot)


        var progress = findViewById<ProgressBar>(R.id.progressBar)

        progress.visibility = View.VISIBLE
        var myQuery = MovieQuery()
        myQuery.execute("") // runs query
    }

    inner class MovieQuery : AsyncTask<String, Integer, String>() {

        var progress = 0
        override fun doInBackground(vararg p0: String?): String {
            try {

                val url = URL("http://www.omdbapi.com/?apikey=6c9862c2&r=xml&t=The+Matrix")
                val urlConnection = url.openConnection() as HttpURLConnection
                val stream = urlConnection.getInputStream()

                val factory = XmlPullParserFactory.newInstance()
                factory.setNamespaceAware(false)
                val xpp = factory.newPullParser()
                xpp.setInput( stream, "UTF-8") //set the input stream

                while(xpp.eventType != XmlPullParser.END_DOCUMENT){

                    when(xpp.eventType){
                        XmlPullParser.START_TAG -> {
                            if(xpp.name.equals("movie")) {

                                title = xpp.getAttributeValue(null, "title")
                                year = xpp.getAttributeValue(null, "year")
                                rating = xpp.getAttributeValue(null, "rated")
                                runtime = xpp.getAttributeValue(null, "runtime")
                                actor = xpp.getAttributeValue(null, "actors")
                                plot = xpp.getAttributeValue(null, "plot")

                                posterIcon = xpp.getAttributeValue(null, "poster")
                                var posterURL = "https://m.media-amazon.com/images/M/MV5BNzQzOTk3OTAtNDQ0Zi00ZTVkLWI0MTEtMDllZjNkYzNjNTc4L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg"
                                movieImage = getImage(URL(posterURL))

                                progress += 100
                            }
                            else if(xpp.name.equals("temperature")) {

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
            currMovieTitle.setText("Current Temp: $title")
            currMovieYear.setText("Minimum Temp: $year")
            currMovieRating.setText("Maximum Temp: $rating")
            currMovieRuntime.setText("Wind Speed: $runtime")
            currMovieActor.setText("Maximum Temp: $actor")
            currMoviePlot.setText("Wind Speed: $plot")

            progressBar.progress = progress
        }

        override fun onPostExecute(result: String?) {
            imageCurrMoviePoster.setImageBitmap(movieImage)
            //progress.visibility = View.INVISIBLE
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
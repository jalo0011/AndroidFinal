package com.algonquincollege.jalo0011.androidfinal


import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

/**
 * A simple [Fragment] subclass.
 *
 */
class MovieFragment : Fragment() {

    var amITablet = false
    var parent: MovieActivity? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var infoPassed = arguments

        var string = infoPassed.getString("Movie")
        var id = infoPassed.getLong("ID")
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_message, container, false)
        var screen = inflater.inflate(R.layout.fragment_movie, container, false)

       // var imageView = screen.findViewById<ImageView>(R.id.moviePoster)
       // imageView.setImageBitmap(Bitmap!)

        var textView1 = screen.findViewById<TextView>(R.id.movieTitle)
        textView1.setText(id.toString())

        var textView2 = screen.findViewById<TextView>(R.id.movieYear)
        textView2.setText(id.toString())

        var textView3 = screen.findViewById<TextView>(R.id.movieRating)
        textView3.setText(id.toString())

        var textView4 = screen.findViewById<TextView>(R.id.movieRuntime)
        textView4.setText(id.toString())

        var textView5 = screen.findViewById<TextView>(R.id.movieActors)
        textView5.setText(id.toString())

        var textView6 = screen.findViewById<TextView>(R.id.moviePlot)
        textView6.setText(id.toString())

        return screen
    }

    override fun onAttach(context: Activity?) {
        super.onAttach(context)
        if (amITablet)
            parent = context as MovieActivity
    }


}

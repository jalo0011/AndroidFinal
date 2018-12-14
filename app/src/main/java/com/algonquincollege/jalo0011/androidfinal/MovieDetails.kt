package com.algonquincollege.jalo0011.androidfinal

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MovieDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        var infoToPass = intent.extras

        var myNewFragment = MovieFragment()
        myNewFragment.arguments = infoToPass
        myNewFragment.amITablet = false

        var loadFragment = getFragmentManager().beginTransaction() // this is a fragement transaction
        loadFragment.replace(R.id.fragment_location, myNewFragment)
        //myNewFragment.arguments = infoToPass
        loadFragment.commit()


    }
}

//Android Final
//Group: Mike, Johshahwhah, Karandeep, Keanu
package com.algonquincollege.jalo0011.androidfinal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {

    //initializing all Buttons
    lateinit var cbc_btn : Button
    lateinit var oc_btn : Button
    lateinit var food_btn : Button
    lateinit var movie_btn : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //linking button variables to button item's from the xml file
        cbc_btn = findViewById(R.id.cbc_btn)
        oc_btn = findViewById(R.id.oc_btn)
        food_btn = findViewById(R.id.food_btn)
        movie_btn = findViewById(R.id.movie_btn)

        //setting intents for each button
        cbc_btn.setOnClickListener {
            var intent = Intent(this@MainActivity,CBC_Activity::class.java)

            startActivity(intent)
        }

        oc_btn.setOnClickListener {
            var intent = Intent(this@MainActivity,OcTranspoActivity::class.java)

            startActivity(intent)
        }

        food_btn.setOnClickListener {
            var intent = Intent(this@MainActivity,FoodActivity::class.java)

            startActivity(intent)
        }

        movie_btn.setOnClickListener {
            var intent = Intent(this@MainActivity,MovieActivity::class.java)

            startActivity(intent)
        }

    }
}

package com.algonquincollege.jalo0011.androidfinal

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import android.view.LayoutInflater
import android.view.ViewGroup
import android.database.sqlite.SQLiteOpenHelper

class CBC_Activity : Activity() {

    var title : String =""
    var description : String=""
    var link : String=""
    var pubDate : String=""
    var author : String=""
    var category : String=""

    val CREATION_STATEMTNT = "CREATE TABLE MESSAGES ( _id INTEGER PRIMARY KEY AUTOINCREMENT," + "aMessage text(string), PRICE INTEGER(int))"
    var messages = ArrayList<String>()  //automatically grows

    var db : SQLiteDatabase? = null
    var messageClicked = 0
    lateinit var theAdapter :MyAdapter
    lateinit var results: Cursor
    lateinit var dbHelper :CBCDatabaseHelper


    lateinit var progress: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cbc_)


        // Starts HERE
        Log.i("CBCDatabaseHelper", "Calling onCreate");
        dbHelper = CBCDatabaseHelper()      // will open the database
        theAdapter = MyAdapter(this)

        db = dbHelper.writableDatabase      // we can read and write to this database

        //run a query, this will return the table with two columns of id and messages with its respective rows
        results = db?.query(dbHelper.TABLE_NAME, arrayOf( dbHelper.KEY_MESSAGE, dbHelper.KEY_ID),
            null, null, null, null, null, null)!!

        var numRows = results.count         // that will tell us the number of rows saved
        results.moveToFirst()       //read from the first row, moves the cursor to the top row

        val keyIndex = results.getColumnIndex(dbHelper.KEY_MESSAGE)        // which column number is KEY_MESSAGE(Messages)

        while (!results.isAfterLast)
        {
            var thisMessage = results.getString(keyIndex)!!
            messages.add(thisMessage)       //pre load messages from database
            results.moveToNext()
        }

        var myList = findViewById<ListView>(R.id.myList)



        myList.adapter=theAdapter
    }

    fun deleteMessage(id:Long)
    {
        //delete the message with id
        db?.delete(dbHelper.TABLE_NAME,"_id=$id", null)

        //run a query, this will return the table with two columns of id and messages with its respective rows
        results = db?.query(dbHelper.TABLE_NAME, arrayOf( dbHelper.KEY_MESSAGE, dbHelper.KEY_ID),
            null, null, null, null, null, null)!!
        messages.removeAt(messageClicked)
        theAdapter.notifyDataSetChanged()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if((requestCode == 35) && (resultCode == Activity.RESULT_OK))
        {
            var num = data!!.getLongExtra("ID", 0)
            deleteMessage(num)
        }
    }

    inner class MyAdapter(ctx : Context) : ArrayAdapter<String>(ctx, 0 ) {

        override fun getCount(): Int {
            return messages.size
        }

        override fun getItem(position: Int): String? {
            return messages.get(position)
        }

        override fun getItemId(position: Int): Long {
            results.moveToPosition(position)
            return results.getInt(results.getColumnIndex("_id")) .toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
            var inflater = LayoutInflater.from(parent.context)

            var thisRow = null as View?
                thisRow = inflater.inflate(R.layout.cbc_tiles, null)
            var textView = thisRow.findViewById<TextView>(R.id.title)
            textView.setText( getItem (position))
            return thisRow
        }

        //return what should be at row position


        // what is the database ID of item at position

    }
    val DATABASE_NAME = "Messages.db"       // name of the database with the name Messages
    val VERSION_NUM = 5                    // version of the initial table you created


    inner class CBCDatabaseHelper : SQLiteOpenHelper(this@CBC_Activity, DATABASE_NAME, null, VERSION_NUM)
    {
        val KEY_ID = "_id"
        val TABLE_NAME = "CBCNewsReader"
        val KEY_MESSAGE = "Messages"

        override fun onCreate(db : SQLiteDatabase)
        {
            db.execSQL("CREATE TABLE  $TABLE_NAME (  _id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + " TEXT)")
            Log.i("CBCDatabaseHelper", "Calling onCreate");

        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME)   //delete all current data
            onCreate(db)    //create new table
            Log.i("CBCDatabaseHelper", "Calling onUpgrade, Old Version: $oldVersion New Version: $newVersion");
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db?.close()
    }

}



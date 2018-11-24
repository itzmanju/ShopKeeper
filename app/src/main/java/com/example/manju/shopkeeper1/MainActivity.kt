package com.example.manju.shopkeeper1

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.content.Intent
import android.os.AsyncTask
import android.view.Menu
import android.view.MenuItem
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

const val SCAN_CONTENT_REQUEST = 1  // The request code


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var tag = "MyMessage"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Do We need a message ?", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        Log.i(tag, "ON Create");

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val billing = findViewById<ImageButton>(R.id.im1)
        val stockTake = findViewById<ImageButton>(R.id.im2)
        var catTextView = findViewById<TextView>(R.id.Result)
        scanContent = findViewById<TextView>(R.id.scan_content) as TextView


        billing.setOnClickListener {
            catTextView.text = "product info"
            val intent = Intent(this@MainActivity, ScanCodeActivityActivity::class.java)
            //startActivity(intent)
            startActivityForResult(intent, SCAN_CONTENT_REQUEST)

        }

        stockTake.setOnClickListener {
            catTextView.text = "not ready yet"
        }
    }

    companion object {

        var scanContent: TextView? = null
    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        // Log.i(tag, scanContent)
         var data = findViewById<TextView>(R.id.scan_content)
         Log.i(tag, data.text.toString())
         // Check which request we're responding to
        if (requestCode == SCAN_CONTENT_REQUEST) {
            // Make sure the request was successful
            if (data.text != null) {
                // Here we need to handle scanned data...
                SendRequest().execute(data.text.toString())
            } else {
                //TODO Handle error case
                Log.i(tag, " NullValue read")
            }
        }
    }
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_invoice -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }

            R.id.nav_share -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

}

class SendRequest : AsyncTask<String, Void, String>() {

    private var tag = "MyMessage"

    protected override fun onPreExecute() {}

    protected override fun doInBackground(vararg arg0: String): String {
        Log.i(tag, " doInBackground")
        //val mystring:String = arg0[0].toString()
        val mystring = arg0[0].toString()

        Log.i(tag, mystring.toString())
        try {

            //Enter script URL Here
            // TODO - This is the published URL of the script
            val url =
                URL("https://script.google.com/macros/s/AKfycbxlX5RyvDdAyJJ4DQ7cdBJ6JnaTyxI-Rayu1mCP1lOF_LdIDvRu/exec")

            val postDataParams = JSONObject()

            //Passing scanned code as parameter

            postDataParams.put("sdata", mystring)


            Log.e("params", postDataParams.toString())
            Log.i(tag, " doInBackground - params")
            val conn = url.openConnection() as HttpURLConnection
            conn.setReadTimeout(15000 /* milliseconds */)
            conn.setConnectTimeout(15000 /* milliseconds */)
            conn.setRequestMethod("GET")
            conn.setDoInput(true)
            conn.setDoOutput(true)

            val os = conn.getOutputStream()
            val writer = BufferedWriter(
                OutputStreamWriter(os, "UTF-8")
            )
            writer.write(getPostDataString(postDataParams))

            writer.flush()
            writer.close()
            os.close()

            val responseCode = conn.getResponseCode()
            val sb = StringBuffer("")
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                Log.i(tag, " HTTP_OK")
                val `in` = BufferedReader(InputStreamReader(conn.getInputStream()))

                var line : String?

                do {
                    line = `in`.readLine()
                    if(line == null)
                        break
                    sb.append(line)
                }while (true)

                `in`.close()
                return sb.toString()

            } else {
                Log.i(tag, " False : $responseCode")
                sb.append("false : $responseCode")
                return sb.toString()
            }
        } catch (e: Exception) {
            Log.i(tag, " Exception: \" + e.message")
            val sb = StringBuffer("")
            sb.append("Exception: " + e.message)
            return sb.toString()
            //return String("Exception: " + e.message)
        }

    }

    protected override fun onPostExecute(result: String) {
          //Toast.makeText(
            //  applicationContext, result,
          //Toast.LENGTH_LONG).show()


    }

    @Throws(Exception::class)
    fun getPostDataString(params: JSONObject): String {

        val result = StringBuilder()
        var first = true

        val itr = params.keys()

        while (itr.hasNext()) {

            val key = itr.next()
            val value = params.get(key)

            if (first)
                first = false
            else
                result.append("&")

            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value.toString(), "UTF-8"))

        }
        return result.toString()
    }
}


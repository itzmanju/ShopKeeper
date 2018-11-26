package com.example.manju.shopkeeper1

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.content.Intent

import android.view.Menu
import android.view.MenuItem
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

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
            startActivity(intent)
            //startActivityForResult(intent, SCAN_CONTENT_REQUEST)

        }

        stockTake.setOnClickListener {
            catTextView.text = "not ready yet"
        }
    }

    companion object {

        var scanContent: TextView? = null
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
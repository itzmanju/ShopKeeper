package com.example.manju.shopkeeper1

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView


const val ADD_CONTENT_REQUEST = 2

class ScanCodeActivityActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var mScannerView: ZXingScannerView? = null
    private var tag = "MyMessage"

    public override fun onCreate(state: Bundle?) {
        Log.i(tag, " ResultHandler ONCreate")
        super.onCreate(state)
        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        Log.i(tag, " ResultHandler onResume")
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        Log.i(tag, " ResultHandler onPause")
        super.onPause()
        mScannerView!!.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        Log.i(tag, " ResultHandler handleResult")
        val scanData = rawResult.text.toString()
        //TODO :  I can remove this. Leaving here till layout modification.
        MainActivity.scanContent!!.setText(rawResult.text)

        if (scanData != null) {
            Log.i(tag, " start intent for AddItem")
            // Start another activity to launch the form and collect data from user
            val intent = Intent(applicationContext, AddItem::class.java)
            intent.putExtra("scanData", scanData)
            startActivity(intent)

        } else {
            //TODO Handle error case
            Log.i(tag, " NullValue read")
        }

        //TODO Is Super call needed here for cleanup ?
        // super.onActivityResult(Result)
        onBackPressed()
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

}

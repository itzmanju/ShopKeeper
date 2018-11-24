package com.example.manju.shopkeeper1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import android.os.AsyncTask
import android.widget.Toast

import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

import javax.net.ssl.HttpsURLConnection


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

        MainActivity.scanContent!!.setText(rawResult.text)
/*
            if (scanData != null) {
                // Here we need to handle scanned data...
                SendRequest().execute(scanData)
            } else {
                //TODO Handle error case
                Log.i(tag, " NullValue read")
            }
*/


        // what is this to do
        // super.onActivityResult(Result)
        onBackPressed()
        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}
/*
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
      //  Toast.makeText(
        //    applicationContext, result,
          //  Toast.LENGTH_LONG
        // ).show()

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

*/
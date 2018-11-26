package com.example.manju.shopkeeper1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.manju.shopkeeper1.R.*

import java.util.HashMap

class AddItem : AppCompatActivity(), View.OnClickListener {

    private var editTextPrefix:EditText? = null
    private var editTextSerialNum: TextView? = null
    private var editTextPartNum:EditText? = null
    private var editTextMfg:EditText? = null
    private var editTextMfgPartNum:EditText? = null
    private var editTextMfgSerialNum:EditText? = null
    private var editTextItemType:EditText? = null
    private var editTextMFD:EditText? = null
    private var editTextDesc:EditText? = null
    private var editTextRev:EditText? = null
    private var buttonAddItem:Button? = null
    var testSerialNum: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layout.add_item)
        //Receive the parameter passed through intent.
        testSerialNum = intent.getStringExtra("scanData")


        editTextPrefix = findViewById<View>(id.et_prefix) as EditText

        editTextSerialNum = findViewById<View>(id.et_serialNum) as TextView
        editTextSerialNum!!.setText(testSerialNum)

        editTextPartNum= findViewById<View>(id.et_partNum) as EditText
        editTextMfg = findViewById<View>(id.et_manufacturer) as EditText
        editTextMfgPartNum = findViewById<View>(id.et_MfgPartNum) as EditText
        editTextMfgSerialNum = findViewById<View>(id.et_MfgSerialNum) as EditText
        editTextItemType = findViewById<View>(id.et_type) as EditText
        editTextMFD = findViewById<View>(id.et_MfgDate) as EditText
        editTextDesc = findViewById<View>(id.et_desc) as EditText
        editTextRev = findViewById<View>(id.et_rev) as EditText
        buttonAddItem = findViewById<View>(id.btn_add_item) as Button
        buttonAddItem!!.setOnClickListener(this)

    }

    private fun addItemToSheet() {

        //TODO : Before intenting to main, Should I call finish to close activity , so that back button wont bring me pack to this activity ?

        val loading = ProgressDialog.show(this, "Adding Item", "Please wait")
        val prefix = editTextPrefix?.text.toString().trim { it <= ' ' }
        val serialnum = testSerialNum
        val pnum = editTextPartNum?.text.toString().trim { it <= ' ' }
        val mfg = editTextMfg?.text.toString().trim { it <= ' ' }
        val mfgpartnum = editTextMfgPartNum?.text.toString().trim { it <= ' ' }
        val mfgserialnum = editTextMfgSerialNum?.text.toString().trim { it <= ' ' }
        val itemtype = editTextItemType?.text.toString().trim { it <= ' ' }
        val mfd = editTextMFD?.text.toString().trim { it <= ' ' }
        val description = editTextDesc?.text.toString().trim { it <= ' ' }
        val rev = editTextRev?.text.toString().trim { it <= ' ' }


        // script published @ https://script.google.com/macros/s/AKfycbxlX5RyvDdAyJJ4DQ7cdBJ6JnaTyxI-Rayu1mCP1lOF_LdIDvRu/exec (now is v3)
        val stringRequest = object : StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbxlX5RyvDdAyJJ4DQ7cdBJ6JnaTyxI-Rayu1mCP1lOF_LdIDvRu/exec",
            Response.Listener { response ->
                loading.dismiss()
                Toast.makeText(this@AddItem, response, Toast.LENGTH_LONG).show()
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            },
            Response.ErrorListener { }
        ) {

            override fun getParams(): Map<String, String> {

                val params = HashMap<String, String>()

                //here we pass parameters
                params["action"] = "addItem"
                params["prefix"] = prefix
                params["serialnum"] = serialnum.toString()
                params["pnum"] = pnum
                params["mfg"] = mfg
                params["mfgpartnum"] = mfgpartnum
                params["mfgserialnum"] = mfgserialnum
                params["itemtype"] = itemtype
                params["mfd"] = mfd
                params["description"] = description
                params["rev"] = rev
                return params
            }
        }

        val socketTimeOut = 50000// u can change this .. here it is 50 seconds
        val retryPolicy = DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        stringRequest.retryPolicy = retryPolicy
        val queue = Volley.newRequestQueue(this)
        queue.add(stringRequest)

    }

    override fun onClick(v: View) {

        if (v === buttonAddItem) {
            addItemToSheet()
            //Define what to do when button is clicked
        }
    }
}
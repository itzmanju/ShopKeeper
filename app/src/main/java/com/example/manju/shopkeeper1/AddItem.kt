package com.example.manju.shopkeeper1

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import com.android.volley.AuthFailureError
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import java.util.HashMap

class AddItem : AppCompatActivity(), View.OnClickListener {

    private var editTextSerialNum:EditText? = null
    private var editTextPartNum:EditText? = null
    private var editTextDesc:EditText? = null
    private var buttonAddItem:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_item)

        editTextSerialNum = findViewById<View>(R.id.et_serialNum) as EditText
        editTextPartNum= findViewById<View>(R.id.et_partNum) as EditText
        editTextDesc = findViewById<View>(R.id.et_desc) as EditText

        buttonAddItem = findViewById<View>(R.id.btn_add_item) as Button
        buttonAddItem!!.setOnClickListener(this)


    }

    private fun addItemToSheet() {

        val loading = ProgressDialog.show(this, "Adding Item", "Please wait")
        val pnum = editTextPartNum?.text.toString().trim { it <= ' ' }
        val description = editTextDesc?.text.toString().trim { it <= ' ' }

        // script published @ https://script.google.com/macros/s/AKfycbxlX5RyvDdAyJJ4DQ7cdBJ6JnaTyxI-Rayu1mCP1lOF_LdIDvRu/exec
        // script published @ https://script.google.com/macros/s/AKfycbxlX5RyvDdAyJJ4DQ7cdBJ6JnaTyxI-Rayu1mCP1lOF_LdIDvRu/exec
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
                val parmas = HashMap<String, String>()

                //here we pass params
                parmas["action"] = "addItem"
                parmas["itemName"] = pnum
                parmas["description"] = description

                return parmas
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
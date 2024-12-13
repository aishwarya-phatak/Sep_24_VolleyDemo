package com.bitcode.sep_24_volleydemo

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.bitcode.sep_24_volleydemo.databinding.ActivityMainBinding
import com.google.gson.Gson
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    var users = ArrayList<User>()
    var pageNumber : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        VolleySingleton.initRequestQueue(this)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.btnCreateUser.setOnClickListener {
            addUser()
        }

        activityMainBinding.btnStringRequest.setOnClickListener {
            stringRequest()
        }

        activityMainBinding.btnJsonObjectRequest.setOnClickListener {
            jsonObjectRequest()
        }
    }

    private fun jsonObjectRequest(){
        var volleyJsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            "https://reqres.in/api/users?page=${pageNumber}",
            null,
            JSONObjectRequestListener(),
            StringRequestErrorListener()
        )

        VolleySingleton.volleyRequestQueue!!.add(volleyJsonObjectRequest)
        pageNumber++
    }

    private fun stringRequest(){
        var volleyStringRequest = StringRequest(
            Request.Method.GET,
            "https://reqres.in/api/users?page=${pageNumber}",
            StringRequestListener(),
            StringRequestErrorListener()
        )

        VolleySingleton.volleyRequestQueue!!.add(volleyStringRequest)
        pageNumber++
    }

    private fun addUser(){
        var inputJsonObject = JSONObject()
        inputJsonObject.put("email",activityMainBinding.edtUserEmail.text.toString())
        inputJsonObject.put("password",activityMainBinding.edtPassword.text.toString())

        var jsonObjectRequestQueue = JsonObjectRequest(
            Request.Method.POST,
            "https://reqres.in/api/register",
            inputJsonObject,
            AddUserListener(),
            StringRequestErrorListener()
        )

        VolleySingleton.volleyRequestQueue!!.add(jsonObjectRequestQueue)
    }

    inner class JSONObjectRequestListener : Response.Listener<JSONObject>{
        override fun onResponse(response: JSONObject?) {

                var usersResponse = Gson().fromJson<UsersResponse>(response.toString(),
                    UsersResponse::class.java)

                users.addAll(usersResponse.data)

                for (eachUser in users){
                    Log.e("tag",eachUser.toString())
                }
            Log.e("tag", "---------------------")
        }
    }

    inner class StringRequestListener : Response.Listener<String>{
        override fun onResponse(response: String?) {
            var usersResponse = Gson().fromJson<UsersResponse>(response,UsersResponse::class.java)
            users.addAll(usersResponse.data)
            for (eachUser in users){
                Log.e("tag", eachUser.toString())
            }
        }
    }

    inner class AddUserListener : Response.Listener<JSONObject>{
        override fun onResponse(response: JSONObject?) {
            Log.e("tag", response.toString())
        }
    }

    inner class StringRequestErrorListener : Response.ErrorListener{
        override fun onErrorResponse(error: VolleyError?) {
           Log.e("tag", error.toString())
        }

    }
}
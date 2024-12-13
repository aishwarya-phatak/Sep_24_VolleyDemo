package com.bitcode.sep_24_volleydemo

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object VolleySingleton {
    var volleyRequestQueue : RequestQueue? = null

    fun initRequestQueue(context: Context){
        volleyRequestQueue = Volley.newRequestQueue(context)  //3 overloads of the method newRequestQueue
    }
}
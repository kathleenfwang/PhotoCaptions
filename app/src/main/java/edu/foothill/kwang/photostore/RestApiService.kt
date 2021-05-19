package edu.foothill.kwang.photostore

import android.util.Log
import com.google.gson.internal.LinkedTreeMap
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    val CV_AZURE_KEY = "847a34790f094bbb897d84b1f258ac2c"
    fun addImage(image: Image, onResult: (Image?) -> Unit){
        val retrofit = ServiceBuilder.buildService(CVService::class.java)
        retrofit.getImage(CV_AZURE_KEY,"Description",image).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    onResult(null)
                }
                override fun onResponse( call: Call<Any>, response: Response<Any>) {
                    val yourMap = response.body();
                }
            }
        )
    }
}
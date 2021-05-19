package edu.foothill.kwang.photostore

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    val CV_AZURE_KEY = "847a34790f094bbb897d84b1f258ac2c"
    fun addImage(image: Image, onResult: (Image?) -> Unit){
        val retrofit = ServiceBuilder.buildService(CVService::class.java)
        retrofit.getImage(CV_AZURE_KEY,"Tags",image).enqueue(
            object : Callback<Any> {
                override fun onFailure(call: Call<Any>, t: Throwable) {
                    Log.d("rest api service", "failed")
                }
                override fun onResponse( call: Call<Any>, response: Response<Any>) {
                    val addedImage = response.body()
                    Log.d("rest api service", "response")
                }
            }
        )
    }
}
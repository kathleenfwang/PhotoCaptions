package edu.foothill.kwang.photostore

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RestApiService {
    val CV_AZURE_KEY = "847a34790f094bbb897d84b1f258ac2c"
    fun addImage(image: Image, onResult: (ImageResult?) -> Unit){
        val retrofit = ServiceBuilder.buildService(CVService::class.java)
        retrofit.getImage(CV_AZURE_KEY,"Tags,Description",image).enqueue(
            object : Callback<ImageResult> {
                override fun onFailure(call: Call<ImageResult>, t: Throwable) {
                    Log.d("rest api service", "failed $t")
                    onResult(null)
                }
                override fun onResponse( call: Call<ImageResult>, response: Response<ImageResult>) {
                    val addedImage = response.body()
                    Log.d("rest api service", "response: $addedImage")
                    onResult(addedImage)
                }
            }
        )
    }
}
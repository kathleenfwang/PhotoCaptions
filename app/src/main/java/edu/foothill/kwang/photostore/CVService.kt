package edu.foothill.kwang.photostore

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.*


public interface CVService {
    // Request method and URL specified in the annotation
    @Headers("Content-Type: application/json")
    @POST("/vision/v2.1/analyze")
    fun getImage(
        @Header("Ocp-Apim-Subscription-Key") authHeader: String,
        @Query("visualFeatures") searchTerm: String,
        @Body image: Image ): Call<ImageResult>
}



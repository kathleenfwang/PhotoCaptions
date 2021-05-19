package edu.foothill.kwang.photostore

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User
import okhttp3.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


public interface CVService {
    // Request method and URL specified in the annotation
    @GET("analyze")
    fun searchImages(
        @Query("visualFeatures") searchTerm: String) :Call<Any>)

}



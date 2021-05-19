package edu.foothill.kwang.photostore

import com.google.gson.annotations.SerializedName
import com.google.gson.internal.LinkedTreeMap

data class Image(
        @field:SerializedName("url") var mUrl: String
)
data class ImageResult(
        val tags: List<Tag>,
        val description: Map<Object,Object>
)
data class Tag(
        val name: String,
        val confidence: Double,
        val hint: String
)

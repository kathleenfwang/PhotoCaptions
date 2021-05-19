package edu.foothill.kwang.photostore

import com.google.gson.annotations.SerializedName


class Image(
    @field:SerializedName("url") var mUrl: String
)
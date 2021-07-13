package com.photon.xome.home.data

import com.google.gson.annotations.SerializedName

data class FlickrImages(
    @SerializedName("page") val page : Int,
    @SerializedName("pages") val pages : Int,
    @SerializedName("perpage") val perpage : Int,
    @SerializedName("total") val total : Int,
    @SerializedName("photo") val flickrImagesList : List<FlickrImage>
)
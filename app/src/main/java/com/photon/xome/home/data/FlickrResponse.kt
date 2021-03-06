package com.photon.xome.home.data

import com.google.gson.annotations.SerializedName

data class FlickrResponse(
    @SerializedName("photos") val flickrImages : FlickrImages,
    @SerializedName("stat") val stat : String
)
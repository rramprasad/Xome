package com.photon.xome.home.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrService {
    @GET("services/rest/")
    fun listFlickrImages(
        @Query("method") method: String,
        @Query("api_key") apiKey: String,
        @Query("format") format: String,
        @Query("nojsoncallback") noJsonCallback: Boolean,
        @Query("safe_search") safeSearch: Boolean,
        @Query("text") searchKeyword: String
    ): Call<FlickrResponse>
}
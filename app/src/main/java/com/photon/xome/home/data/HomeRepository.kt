package com.photon.xome.home.data

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class HomeRepository @Inject constructor() {
    private val LOG_TAG: String = "HomeRepository"
    //@Inject lateinit var flickrService: FlickrService
    @Inject lateinit var retrofit: Retrofit

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun getFlickrImages(searchKeyword : String): Flow<List<FlickrImage>> {
        return callbackFlow<List<FlickrImage>> {
            val value = object : Callback<FlickrResponse> {
                override fun onResponse(
                    call: Call<FlickrResponse>,
                    response: Response<FlickrResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let { flickrResponse ->
                            val flickrImagesList = flickrResponse.flickrImages.flickrImagesList
                            trySend(flickrImagesList)
                        }
                    } else {
                        Log.d(LOG_TAG, "onResponse: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<FlickrResponse>, t: Throwable) {
                    Log.d(LOG_TAG, "onResponse: ${t.message}")
                }
            }
            retrofit.create(FlickrService::class.java).listFlickrImages(
                method = "flickr.photos.search",
                apiKey = "3e7cc266ae2b0e0d78e279ce8e361736",
                format = "json",
                noJsonCallback = true,
                safeSearch = true,
                searchKeyword = searchKeyword
            ).enqueue(value)

            awaitClose {
                // cancel listener
            }
        }
    }
}
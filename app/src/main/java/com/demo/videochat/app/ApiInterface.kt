package com.demo.videochat.app

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {

    @GET("v2/locations/markers?proximity_square=100&limit=10")
    fun getPhotos(): Call<VideoItemModel>

}
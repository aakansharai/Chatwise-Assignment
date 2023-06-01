package com.android.assigment


import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    fun getImages(): retrofit2.Call<List<ImageModel>>
}

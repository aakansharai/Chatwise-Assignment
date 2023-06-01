package com.android.assigment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.security.auth.callback.Callback

class ImageViewModel : ViewModel() {

    private val images: MutableLiveData<List<String>> = MutableLiveData()

    fun getImages(): LiveData<List<String>> {
        fetchImages()
        return images
    }

    private fun fetchImages() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        val call = api.getImages()

        call.enqueue(object :retrofit2.Callback<List<ImageModel>> {
            override fun onResponse(call: Call<List<ImageModel>>, response: Response<List<ImageModel>>) {
                if (response.isSuccessful) {
                    val imageUrls = response.body()?.map { it.url } ?: emptyList()
                    images.value = imageUrls
                }
            }

            override fun onFailure(call: Call<List<ImageModel>>, t: Throwable) {
                // Handle failure
            }
        })
    }
}

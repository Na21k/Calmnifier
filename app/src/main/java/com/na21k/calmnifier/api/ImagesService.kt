package com.na21k.calmnifier.api

import com.na21k.calmnifier.model.ImageModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ImagesService {
    @GET("images/{id}")
    fun getById(@Path("id") id: String): Call<ImageModel>
}

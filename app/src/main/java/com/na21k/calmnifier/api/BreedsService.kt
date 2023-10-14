package com.na21k.calmnifier.api

import com.na21k.calmnifier.model.BreedModel
import retrofit2.Call
import retrofit2.http.GET

interface BreedsService {
    @GET("breeds")
    fun list(): Call<List<BreedModel>>
}

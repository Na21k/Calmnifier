package com.na21k.calmnifier

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"
const val CAT_API_IMAGES_ENDPOINT_NAME = "images/"

val RETROFIT: Retrofit = Retrofit.Builder()
    .baseUrl(CAT_API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

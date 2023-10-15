package com.na21k.calmnifier

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val CAT_API_BASE_URL = "https://api.thecatapi.com/v1/"

val GSON: Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create()

val RETROFIT: Retrofit = Retrofit.Builder()
    .baseUrl(CAT_API_BASE_URL)
    .addConverterFactory(GsonConverterFactory.create(GSON))
    .build()

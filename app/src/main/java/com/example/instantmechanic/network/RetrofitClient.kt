package com.example.instantmechanic.network

import com.example.instantmechanic.data.api.MechanicApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val API_BASE = "https://6a97b65a0e3240db9061fe83.mockapi.io/"
    val retrofit: Retrofit by lazy {
        Retrofit.Builder().baseUrl(API_BASE)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val mechanicApi: MechanicApi by lazy {
        retrofit.create(MechanicApi::class.java)
    }
}
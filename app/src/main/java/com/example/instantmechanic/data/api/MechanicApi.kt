package com.example.instantmechanic.data.api

import com.example.instantmechanic.data.model.Mechanic
import retrofit2.http.GET

interface MechanicApi {
    @GET("mechanics")
    suspend fun getMechanic(): List<Mechanic>
}
package com.example.instantmechanic.data.repository

import com.example.instantmechanic.data.model.Mechanic
import com.example.instantmechanic.network.RetrofitClient

class MechanicRepository {
    suspend fun getMechanic(): Result<List<Mechanic>>{
        return try {
            val mechanic = RetrofitClient.mechanicApi.getMechanic()
            Result.success(mechanic)
        }
        catch (e: Exception){
            Result.failure(e)
        }
    }
}
package com.example.instantmechanic.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mechanic(
    val id: Int,
    val name: String,
    val rating: Double,
    val distance: String,
    val location: String,
    val address: String,
    val services: List<String>,
    val open: Boolean,
    val workingHours: String,
    val phone: String
) : Parcelable
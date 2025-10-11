package rmc.presentation.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class CreateCar (
    val licensePlate: String,
    val brand: String,
)
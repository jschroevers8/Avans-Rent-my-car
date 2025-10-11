package rmc.presentation.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class CarResponse(
    val id: Int? = null,
    val licensePlate: String,
    val brand: String,
)

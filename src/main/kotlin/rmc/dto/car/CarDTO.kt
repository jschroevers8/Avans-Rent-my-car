package rmc.dto.car

import kotlinx.serialization.Serializable

@Serializable
data class CarDTO (
    val id: Int,
    val brand: String,
)
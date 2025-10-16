package rmc.presentation.dto.image

import kotlinx.serialization.Serializable

@Serializable
data class CarImageResponse(
    val id: Int? = null,
    val carId: Int,
    val image: String,
    val weight: Int,
)

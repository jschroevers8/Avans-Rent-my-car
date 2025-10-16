package rmc.domain.entities

data class CarImageEntity(
    val id: Int? = null,
    val carId: Int,
    val image: String,
    val weight: Int,
)

package rmc.presentation.dto.advertisement

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import rmc.presentation.dto.address.CreateAddress

@Serializable
data class UpdateAdvertisement(
    val carId: Int,
    val address: CreateAddress,
    val availableFrom: LocalDateTime,
    val availableUntil: LocalDateTime,
    val price: Double,
)

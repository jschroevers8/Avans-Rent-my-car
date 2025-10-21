package rmc.presentation.dto.advertisement

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import rmc.presentation.dto.address.AddressResponse

@Serializable
data class AdvertisementResponse(
    val id: Int? = null,
    val carId: Int,
    val address: AddressResponse,
    val availableFrom: LocalDateTime,
    val availableUntil: LocalDateTime,
    val price: Double,
)

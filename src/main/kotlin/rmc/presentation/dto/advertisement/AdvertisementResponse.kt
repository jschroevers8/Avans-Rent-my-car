package rmc.presentation.dto.advertisement

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import rmc.domain.entities.BodyType
import rmc.domain.entities.FuelType
import rmc.presentation.dto.address.AddressResponse

@Serializable
data class AdvertisementResponse(
    val id: Int? = null,
    val carId: Int,
    val address: AddressResponse,
    val pickUpDate: LocalDateTime,
    val returningDate: LocalDateTime,
    val price: Double,
)

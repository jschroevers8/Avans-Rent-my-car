package rmc.presentation.dto.advertisement

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import rmc.domain.entities.BodyType
import rmc.domain.entities.FuelType
import rmc.presentation.dto.address.AddressResponse
import rmc.presentation.dto.address.CreateAddress

@Serializable
data class CreateAdvertisement (
    val carId: Int,
    val address: CreateAddress,
    val pickUpDate: LocalDateTime,
    val returningDate: LocalDateTime,
    val price: Double,
)
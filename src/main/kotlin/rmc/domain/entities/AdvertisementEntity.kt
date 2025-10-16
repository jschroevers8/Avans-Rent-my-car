package rmc.domain.entities

import kotlinx.datetime.LocalDateTime

data class AdvertisementEntity(
    val id: Int? = null,
    val carId: Int,
    val address: AddressEntity,
    val pickUpDate: LocalDateTime,
    val returningDate: LocalDateTime,
    val price: Double,
)

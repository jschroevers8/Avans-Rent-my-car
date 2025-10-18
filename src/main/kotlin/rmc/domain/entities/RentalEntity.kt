package rmc.domain.entities

import kotlinx.datetime.LocalDateTime

data class RentalEntity(
    val id: Int? = null,
    val userId: Int,
    val advertisementId: Int,
    val rentalStatus: RentalStatus,
    val createdStamp: LocalDateTime,
)

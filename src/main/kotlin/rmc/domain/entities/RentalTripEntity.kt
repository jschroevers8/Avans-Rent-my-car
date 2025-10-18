package rmc.domain.entities

import kotlinx.datetime.LocalDateTime

data class RentalTripEntity(
    val id: Int? = null,
    val rentalId: Int,
    val startMileage: Int,
    val endMileage: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)

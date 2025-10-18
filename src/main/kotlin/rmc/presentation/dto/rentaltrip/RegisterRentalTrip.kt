package rmc.presentation.dto.rentaltrip

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRentalTrip(
    val rentalId: Int,
    val startMileage: Int,
    val endMileage: Int,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
)

package rmc.presentation.mappers

import rmc.domain.entities.RentalTripEntity
import rmc.presentation.dto.rentaltrip.RentalTripResponse

fun RentalTripEntity.toResponse() =
    RentalTripResponse(
        id = id,
        rentalId = rentalId,
        startMileage = startMileage,
        endMileage = endMileage,
        startDate = startDate,
        endDate = endDate,
    )

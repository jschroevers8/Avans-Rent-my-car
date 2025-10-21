package rmc.presentation.mappers

import rmc.domain.entities.RentalEntity
import rmc.presentation.dto.rental.RentalResponse

fun RentalEntity.toResponse() =
    RentalResponse(
        id = id,
        userId = userId,
        advertisementId = advertisementId,
        rentalStatus = rentalStatus,
        pickUpDate = pickUpDate,
        returningDate = returningDate,
        rentalTrips = rentalTrips.map { it.toResponse() },
    )

package rmc.presentation.mappers

import rmc.domain.entities.AdvertisementEntity
import rmc.presentation.dto.advertisement.AdvertisementResponse

fun AdvertisementEntity.toResponse() =
    AdvertisementResponse(
        id = id,
        carId = carId,
        address = address.toResponse(),
        availableFrom = availableFrom,
        availableUntil = availableUntil,
        price = price,
    )

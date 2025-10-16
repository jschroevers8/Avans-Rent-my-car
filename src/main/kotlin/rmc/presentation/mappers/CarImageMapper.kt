package rmc.presentation.mappers

import rmc.domain.entities.CarImageEntity
import rmc.presentation.dto.image.CarImageResponse

fun CarImageEntity.toResponse() =
    CarImageResponse(
        id = id,
        carId = carId,
        image = image,
        weight = weight,
    )

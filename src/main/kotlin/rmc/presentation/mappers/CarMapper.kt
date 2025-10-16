package rmc.presentation.mappers

import rmc.domain.entities.CarEntity
import rmc.presentation.dto.car.CarResponse

fun CarEntity.toResponse() =
    CarResponse(
        id = id,
        fuelType = fuelType,
        userId = userId,
        bodyType = bodyType,
        brand = brand,
        modelYear = modelYear,
        licensePlate = licensePlate,
        mileage = mileage,
        createdStamp = createdStamp,
        carImages = carImages.map { it.toResponse() },
    )

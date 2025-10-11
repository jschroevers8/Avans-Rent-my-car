package rmc.presentation.mappers

import rmc.domain.entities.CarEntity
import rmc.presentation.dto.car.CarResponse

fun CarEntity.toResponse() = CarResponse(
    id = id,
    licensePlate = licensePlate,
    brand = brand,
)
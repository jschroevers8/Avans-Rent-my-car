package rmc.presentation.mappers

import rmc.domain.entities.AddressEntity
import rmc.presentation.dto.address.AddressResponse

fun AddressEntity.toResponse() =
    AddressResponse(
        id = id ?: 0,
        city = city,
        street = street,
        houseNumber = houseNumber,
        subHouseNumber = subHouseNumber,
        postalCode = postalCode,
    )

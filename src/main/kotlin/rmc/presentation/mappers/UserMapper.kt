package rmc.presentation.mappers

import rmc.domain.entities.AddressEntity
import rmc.domain.entities.UserEntity
import rmc.presentation.dto.address.CreateAddress
import rmc.presentation.dto.address.AddressResponse
import rmc.presentation.dto.user.CreateUser
import rmc.presentation.dto.user.UserResponse

fun CreateAddress.toEntity() = AddressEntity(
    city = city,
    street = street,
    houseNumber = houseNumber,
    subHouseNumber = subHouseNumber,
    postalCode = postalCode,
)

fun AddressEntity.toResponse() = AddressResponse(
    id = id ?: 0,
    city = city,
    street = street,
    houseNumber = houseNumber,
    subHouseNumber = subHouseNumber,
    postalCode = postalCode,
)

fun CreateUser.toEntity() = UserEntity(
    id = null,
    userType = userType,
    address = address.toEntity(),
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    userPoints = userPoints,
)

fun UserEntity.toResponse() = UserResponse(
    id = id ?: 0,
    userType = userType,
    address = address.toResponse(),
    email = email,
    password = password,
    firstName = firstName,
    lastName = lastName,
    phone = phone,
    userPoints = userPoints,
)
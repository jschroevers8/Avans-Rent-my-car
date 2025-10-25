package rmc.presentation.mappers

import rmc.domain.entities.UserEntity
import rmc.presentation.dto.user.UserResponse

fun UserEntity.toResponse() =
    UserResponse(
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

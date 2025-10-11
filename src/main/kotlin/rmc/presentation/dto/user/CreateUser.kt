package rmc.presentation.dto.user

import kotlinx.serialization.Serializable
import rmc.domain.entities.UserType
import rmc.presentation.dto.address.CreateAddress

@Serializable
data class CreateUser(
    val userType: UserType,
    val address: CreateAddress,
    val email: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val userPoints: Int
)
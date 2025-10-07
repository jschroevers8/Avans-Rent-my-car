package rmc.dto.user

import kotlinx.serialization.Serializable
import rmc.dto.address.AddressDTO

@Serializable
data class UserDTO(
    val id: Int,
    val userType: String,
    val address: AddressDTO,
    val email: String,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val userPoints: Int
)

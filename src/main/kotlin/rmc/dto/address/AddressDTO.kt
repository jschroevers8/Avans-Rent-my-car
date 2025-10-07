package rmc.dto.address

import kotlinx.serialization.Serializable

@Serializable
data class AddressDTO(
    val id: Int,
    val city: String,
    val street: String,
    val houseNumber: Int,
    val subHouseNumber: String?,
    val postalCode: String
)
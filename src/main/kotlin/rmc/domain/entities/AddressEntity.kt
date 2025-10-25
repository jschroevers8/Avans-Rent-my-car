package rmc.domain.entities

data class AddressEntity(
    val id: Int? = null,
    val city: String,
    val street: String,
    val houseNumber: Int,
    val subHouseNumber: String? = null,
    val postalCode: String,
)

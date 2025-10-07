package rmc.dto.car

import kotlinx.serialization.Serializable
import rmc.dto.user.UserDTO

@Serializable
data class CarDTO(
    val id: Int,
    val brand: String,
    val model: String,
    val modelYear: Int,
    val licensePlate: String,
    val mileage: Int,
    val fuelType: String,
    val bodyType: String,
    val userDTO: UserDTO
)

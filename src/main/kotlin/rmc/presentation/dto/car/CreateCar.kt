package rmc.presentation.dto.car

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import rmc.domain.entities.BodyType
import rmc.domain.entities.FuelType
import rmc.presentation.dto.image.CreateCarImage

@Serializable
data class CreateCar(
    val fuelType: FuelType,
    val userId: Int,
    val bodyType: BodyType,
    val brand: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val createdStamp: LocalDateTime,
    val carImages: List<CreateCarImage> = emptyList(),
)

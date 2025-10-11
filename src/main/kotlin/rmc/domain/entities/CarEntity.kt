package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Column


data class CarEntity (
    val id: Int? = null,
    val fuelType: FuelType,
    val userId: Int,
    val bodyType: BodyType,
    val brand: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val createdStamp: LocalDateTime,
)

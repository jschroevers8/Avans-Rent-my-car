package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import rmc.domain.exceptions.UnauthorizedCarAccessException

data class CarEntity(
    val id: Int? = null,
    val fuelType: FuelType,
    val userId: Int,
    val bodyType: BodyType,
    val brand: String,
    val model: String,
    val modelYear: String,
    val licensePlate: String,
    val mileage: String,
    val createdStamp: LocalDateTime,
    var carImages: List<CarImageEntity> = emptyList(),
) {
    fun setImages(carImages: List<CarImageEntity>) {
        this.carImages = carImages
    }

    fun ensureOwnedBy(userId: Int) {
        if (this.userId != userId) {
            throw UnauthorizedCarAccessException("User $userId is not the owner of car $id")
        }
    }
}

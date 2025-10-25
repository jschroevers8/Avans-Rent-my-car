package rmc.domain.entities

import kotlinx.datetime.LocalDateTime

data class AdvertisementEntity(
    val id: Int? = null,
    val carId: Int,
    val address: AddressEntity,
    val availableFrom: LocalDateTime,
    val availableUntil: LocalDateTime,
    val price: Double,
) {
    fun canBeDeleted(rentals: List<RentalEntity>): Boolean =
        rentals.none { it.rentalStatus == RentalStatus.ACTIVE || it.rentalStatus == RentalStatus.PENDING }
}

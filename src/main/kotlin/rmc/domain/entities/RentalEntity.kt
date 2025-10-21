package rmc.domain.entities

import kotlinx.datetime.LocalDateTime

data class RentalEntity(
    val id: Int? = null,
    val userId: Int,
    val advertisementId: Int,
    val rentalStatus: RentalStatus,
    val pickUpDate: LocalDateTime,
    val returningDate: LocalDateTime,
    var rentalTrips: List<RentalTripEntity> = emptyList(),
) {
    fun setTrips(rentalTrips: List<RentalTripEntity>) {
        this.rentalTrips = rentalTrips
    }
}

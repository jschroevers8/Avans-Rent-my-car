package rmc.domain.entities

import kotlinx.datetime.LocalDateTime
import rmc.domain.exceptions.RentalCannotBeCancelledException
import rmc.domain.exceptions.RentalNotPendingException

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

    fun ensureStatusPending() {
        if (this.rentalStatus != RentalStatus.PENDING) {
            throw RentalNotPendingException("Rental with id ${this.id} is not pending and cannot be approved")
        }
    }

    fun ensureStatusNotActiveOrCompleted() {
        if (this.rentalStatus == RentalStatus.ACTIVE || this.rentalStatus == RentalStatus.COMPLETED) {
            throw RentalCannotBeCancelledException("Rental with id  ${this.id} cannot be cancelled because it is ${this.rentalStatus}")
        }
    }
}

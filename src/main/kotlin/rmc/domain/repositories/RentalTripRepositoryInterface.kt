package rmc.domain.repositories

import rmc.domain.entities.RentalTripEntity

interface RentalTripRepositoryInterface {
    fun save(rentalTrip: RentalTripEntity): RentalTripEntity

    fun findByRentalId(rentalId: Int): List<RentalTripEntity>
}

package rmc.domain.repositories

import rmc.domain.entities.RentalTripEntity

interface RentalTripRepositoryInterface {
    fun findById(id: Int): RentalTripEntity?

    fun save(rentalTrip: RentalTripEntity): RentalTripEntity

    fun getAll(): List<RentalTripEntity>

    fun delete(id: Int): Boolean
}

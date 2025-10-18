package rmc.domain.repositories

import rmc.domain.entities.RentalTripEntity

interface RentalTripRepositoryInterface {
    fun findById(id: Int): RentalTripEntity?

    suspend fun save(rentalTrip: RentalTripEntity): RentalTripEntity

    suspend fun getAll(): List<RentalTripEntity>

    suspend fun delete(id: Int): Boolean
}

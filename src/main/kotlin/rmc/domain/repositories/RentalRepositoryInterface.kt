package rmc.domain.repositories

import rmc.domain.entities.RentalEntity

interface RentalRepositoryInterface {
    fun findById(id: Int): RentalEntity?

    suspend fun save(rental: RentalEntity): RentalEntity

    suspend fun getAll(): List<RentalEntity>

    suspend fun delete(id: Int): Boolean
}

package rmc.domain.repositories

import rmc.domain.entities.RentalEntity

interface RentalRepositoryInterface {
    fun findById(id: Int): RentalEntity?

    fun save(rental: RentalEntity): RentalEntity

    fun getAll(): List<RentalEntity>

    fun delete(id: Int): Boolean
}

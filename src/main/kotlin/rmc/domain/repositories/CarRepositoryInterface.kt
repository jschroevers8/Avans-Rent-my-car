package rmc.domain.repositories

import rmc.domain.entities.CarEntity

interface CarRepositoryInterface {
    fun findById(id: Int): CarEntity?

    suspend fun findByLicensePlate(licensePlate: String): CarEntity?

    suspend fun save(car: CarEntity): CarEntity

    suspend fun delete(id: Int): Boolean

    suspend fun getAll(): List<CarEntity>
}

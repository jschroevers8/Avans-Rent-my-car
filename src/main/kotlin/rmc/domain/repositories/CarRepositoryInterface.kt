package rmc.domain.repositories

import rmc.domain.entities.CarEntity

interface CarRepositoryInterface {
    fun getAllCarsByUserId(userId: Int): List<CarEntity>

    fun findById(id: Int): CarEntity?

    fun findByLicensePlate(licensePlate: String): CarEntity?

    fun save(car: CarEntity): CarEntity

    fun delete(id: Int): Boolean

    fun getAll(): List<CarEntity>
}

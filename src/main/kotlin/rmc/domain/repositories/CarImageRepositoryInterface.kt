package rmc.domain.repositories

import rmc.domain.entities.CarImageEntity

interface CarImageRepositoryInterface {
    fun findByCarId(carId: Int): List<CarImageEntity>

    fun save(carImage: CarImageEntity): CarImageEntity
}

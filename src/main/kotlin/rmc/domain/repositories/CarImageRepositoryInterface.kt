package rmc.domain.repositories

import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity

interface CarImageRepositoryInterface {
    fun findByCarId(carId: Int): List<CarImageEntity>

    fun deleteAllByCar(car: CarEntity): Boolean

    fun save(carImage: CarImageEntity): CarImageEntity
}

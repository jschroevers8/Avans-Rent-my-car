package rmc.repository.car

import rmc.dto.car.CarDTO


interface CarRepositoryInterface {
    suspend fun getAllCars(): List<CarDTO>
}
package rmc.presentation.controllers

import rmc.application.services.CarService
import rmc.presentation.dto.car.CarResponse
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.mappers.toResponse

class CarController(private val carService: CarService) {
    suspend fun createCar(request: CreateCar): CarResponse {
        val car = carService.create(request)

        return car.toResponse()
    }

    suspend fun findCarById(id: Int): CarResponse? = carService.findCarById(id)?.toResponse()

    suspend fun getAllCars(): List<CarResponse> = carService.getAllCars().map { it.toResponse() }
}

package rmc.application.services

import rmc.application.exceptions.NotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarRepositoryInterface
import rmc.presentation.dto.car.CreateCar

class CarService (
    private val carRepository: CarRepositoryInterface,
//    private val userRepository: UserRepositoryInterface,
) {
    suspend fun getAllCars(): List<CarEntity> = carRepository.getAll()


    suspend fun create(carRequest: CreateCar): CarEntity {
//        val user = userRepository.findById(userId)
//            ?: throw NotFoundException("User not found")

        if (carRepository.findByLicensePlate(carRequest.licensePlate) != null) {
            throw NotFoundException("Car with this license plate already exists")
        }

        val car = CarEntity(
            licensePlate = carRequest.licensePlate,
            brand = carRequest.brand,
        )

        return carRepository.save(car)
    }
}
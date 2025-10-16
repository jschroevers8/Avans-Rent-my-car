package rmc.application.usecases.car

import rmc.application.exceptions.NotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.car.CreateCar

class CreateCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
) {
    suspend fun invoke(carRequest: CreateCar): CarEntity {
        if (userRepository.findById(carRequest.userId) == null) {
            throw NotFoundException("User not found")
        }

        if (carRepository.findByLicensePlate(carRequest.licensePlate) != null) {
            throw NotFoundException("Car with this license plate already exists")
        }

        val car =
            CarEntity(
                fuelType = carRequest.fuelType,
                userId = carRequest.userId,
                bodyType = carRequest.bodyType,
                brand = carRequest.brand,
                modelYear = carRequest.modelYear,
                licensePlate = carRequest.licensePlate,
                mileage = carRequest.mileage,
                createdStamp = carRequest.createdStamp,
            )

        return carRepository.save(car)
    }
}

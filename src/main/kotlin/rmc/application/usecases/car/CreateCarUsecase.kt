package rmc.application.usecases.car

import rmc.application.exceptions.CarAlreadyExistsException
import rmc.application.exceptions.UserNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.entities.UserType
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.car.CreateCar

class CreateCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(carRequest: CreateCar): CarEntity {
        val userId = carRequest.userId
        val user =
            userRepository.findById(userId)
                ?: throw UserNotFoundException("User with id $userId not found")

        if (user.userType != UserType.CUSTOMER) {
            throw IllegalAccessException("Only customers can add a car")
        }

        carRepository.findByLicensePlate(carRequest.licensePlate)?.let {
            throw CarAlreadyExistsException("Car with this license plate already exists")
        }

        val car =
            CarEntity(
                fuelType = carRequest.fuelType,
                userId = carRequest.userId,
                bodyType = carRequest.bodyType,
                brand = carRequest.brand,
                model = carRequest.model,
                modelYear = carRequest.modelYear,
                licensePlate = carRequest.licensePlate,
                mileage = carRequest.mileage,
                createdStamp = carRequest.createdStamp,
            )

        val carTable = carRepository.save(car)

        val images = mutableListOf<CarImageEntity>()

        var weightCounter = 1

        carRequest.carImages.forEach { image ->
            val carImage =
                CarImageEntity(
                    image = image.image,
                    weight = weightCounter,
                    carId = carTable.id!!,
                )
            images += carImageRepository.save(carImage)
            weightCounter++
        }

        carTable.setImages(images)

        return carTable
    }
}

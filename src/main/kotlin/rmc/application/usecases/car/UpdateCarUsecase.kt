package rmc.application.usecases.car

import rmc.application.exceptions.CarAlreadyExistsException
import rmc.application.exceptions.CarNotFoundException
import rmc.application.exceptions.UserNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.entities.UserType
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.car.UpdateCar
import rmc.presentation.dto.image.CreateCarImage

class UpdateCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(carRequest: UpdateCar): CarEntity {
        val user =
            userRepository.findById(carRequest.userId)
                ?: throw UserNotFoundException("User with id ${carRequest.userId} not found")

        if (user.userType != UserType.CUSTOMER) {
            throw IllegalAccessException("Only customers can update a car")
        }

        val existingCar =
            carRepository.findById(carRequest.id!!)
                ?: throw CarNotFoundException("Car with id ${carRequest.id} not found")

        carRepository.findByLicensePlate(carRequest.licensePlate)?.let {
            if (it.id != existingCar.id) {
                throw CarAlreadyExistsException("Another car with this license plate already exists")
            }
        }

        val updatedCar =
            existingCar.copy(
                fuelType = carRequest.fuelType,
                bodyType = carRequest.bodyType,
                brand = carRequest.brand,
                model = carRequest.model,
                modelYear = carRequest.modelYear,
                licensePlate = carRequest.licensePlate,
                mileage = carRequest.mileage,
            )

        val savedCar = carRepository.save(updatedCar)

        // Oude afbeeldingen verwijderen
        carImageRepository.deleteAllByCar(savedCar)

        // Afbeeldingen van DTO
        val images = carRequest.carImages.toMutableList()

        // Voeg één extra afbeelding toe
        images.add(CreateCarImage("extra-image.jpg"))

        // Opslaan van afbeeldingen
        val savedImages =
            images.mapIndexed { index, image ->
                val carImage =
                    CarImageEntity(
                        image = image.image,
                        weight = index + 1,
                        carId = savedCar.id!!,
                    )
                carImageRepository.save(carImage)
            }

        // Koppel afbeeldingen aan auto
        savedCar.setImages(savedImages)

        return savedCar
    }
}

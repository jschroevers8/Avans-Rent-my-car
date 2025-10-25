package rmc.application.usecases.car

import rmc.application.exceptions.CarNotFoundException
import rmc.application.exceptions.UserNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.domain.validations.ExistingCarValidator
import rmc.presentation.dto.car.UpdateCar
import rmc.presentation.dto.image.CreateCarImage

class UpdateCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val existingCarValidator: ExistingCarValidator,
    private val userRepository: UserRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(
        carRequest: UpdateCar,
        userId: Int,
    ): CarEntity {
        val user =
            userRepository.findById(userId)
                ?: throw UserNotFoundException("User with id $userId not found")

        user.ensureCustomer()

        val existingCar =
            carRepository.findById(carRequest.id!!)
                ?: throw CarNotFoundException("Car with id ${carRequest.id} not found")

        existingCar.ensureOwnedBy(userId)
        if (existingCar.licensePlate != carRequest.licensePlate) {
            existingCarValidator(carRequest.licensePlate)
        }

        val carEntity = createCarEntity(existingCar, carRequest)

        val car = carRepository.save(carEntity)

        return saveCarWithImages(car, carRequest.carImages)
    }

    private fun createCarEntity(
        car: CarEntity,
        request: UpdateCar,
    ): CarEntity =
        car.copy(
            fuelType = request.fuelType,
            bodyType = request.bodyType,
            brand = request.brand,
            model = request.model,
            modelYear = request.modelYear,
            licensePlate = request.licensePlate,
            mileage = request.mileage,
        )

    private fun saveCarWithImages(
        car: CarEntity,
        imagesRequest: List<CreateCarImage>,
    ): CarEntity {
        requireNotNull(car.id) { "Cannot create car images without car ID" }

        carImageRepository.deleteAllByCar(car)

        val images =
            imagesRequest.mapIndexed { index, imageRequest ->
                carImageRepository.save(
                    CarImageEntity(
                        image = imageRequest.image,
                        weight = index + 1,
                        carId = car.id,
                    ),
                )
            }

        car.setImages(images)

        return car
    }
}

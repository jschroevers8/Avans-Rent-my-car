package rmc.application.usecases.car

import rmc.application.exceptions.UserNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.entities.CarImageEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.domain.validations.ExistingCarValidator
import rmc.presentation.dto.car.CreateCar
import rmc.presentation.dto.image.CreateCarImage
import kotlin.collections.mapIndexed

class CreateCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val existingCarValidator: ExistingCarValidator,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(
        carRequest: CreateCar,
        userId: Int,
    ): CarEntity {
        val user =
            userRepository.findById(userId)
                ?: throw UserNotFoundException("User with id $userId not found")

        user.ensureCustomer()

        existingCarValidator(carRequest.licensePlate)

        val carEntity = createCarEntity(carRequest, userId)

        return saveCarWithImages(carRepository.save(carEntity), carRequest.carImages)
    }

    private fun createCarEntity(
        request: CreateCar,
        userId: Int,
    ): CarEntity =
        CarEntity(
            fuelType = request.fuelType,
            userId = userId,
            bodyType = request.bodyType,
            brand = request.brand,
            model = request.model,
            modelYear = request.modelYear,
            licensePlate = request.licensePlate,
            mileage = request.mileage,
            createdStamp = request.createdStamp,
        )

    private fun saveCarWithImages(
        car: CarEntity,
        imagesRequest: List<CreateCarImage>,
    ): CarEntity {
        requireNotNull(car.id) { "Cannot create car images without car ID" }

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

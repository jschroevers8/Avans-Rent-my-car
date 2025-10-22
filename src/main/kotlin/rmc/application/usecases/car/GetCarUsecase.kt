package rmc.application.usecases.car

import rmc.application.exceptions.CarNotFoundException
import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface

class GetCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(carId: Int): CarEntity {
        val car =
            carRepository.findById(carId)
                ?: throw CarNotFoundException("Car with id $carId not found")

        val images = carImageRepository.findByCarId(car.id!!)

        car.setImages(images)

        return car
    }
}

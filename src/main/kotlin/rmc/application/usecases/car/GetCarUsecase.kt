package rmc.application.usecases.car

import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface

class GetCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    fun invoke(carId: Int): CarEntity? {
        val car = carRepository.findById(carId) ?: return null

        val images = carImageRepository.findByCarId(car.id!!)
        car.setImages(images) // or car.images = images if using a property

        return car
    }
}

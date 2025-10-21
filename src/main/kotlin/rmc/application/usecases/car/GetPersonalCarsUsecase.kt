package rmc.application.usecases.car

import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface

class GetPersonalCarsUsecase(
    private val carRepository: CarRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(userId: Int): List<CarEntity> {
        val cars = carRepository.getAllCarsByUser(userId)

        for (car in cars) {
            val images = carImageRepository.findByCarId(car.id!!)
            car.setImages(images) // or car.images = images if using a property
        }

        return cars
    }
}

package rmc.application.usecases.car

import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface

class DeleteCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
) {
    operator fun invoke(carId: Int): Boolean {
        val car = carRepository.findById(carId) ?: return false

        carImageRepository.deleteAllByCar(car)

        return carRepository.delete(carId)
    }
}

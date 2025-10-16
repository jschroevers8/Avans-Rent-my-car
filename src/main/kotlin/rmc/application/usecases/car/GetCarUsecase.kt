package rmc.application.usecases.car

import rmc.domain.entities.CarEntity
import rmc.domain.repositories.CarRepositoryInterface

class GetCarUsecase(
    private val carRepository: CarRepositoryInterface,
) {
    fun invoke(carId: Int): CarEntity? = carRepository.findById(carId)
}

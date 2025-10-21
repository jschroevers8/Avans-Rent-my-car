package rmc.application.usecases.car

import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarImageRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface

class DeleteCarUsecase(
    private val carRepository: CarRepositoryInterface,
    private val carImageRepository: CarImageRepositoryInterface,
    private val advertisementRepository: AdvertisementRepositoryInterface,
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(carId: Int): Boolean {
        val car = carRepository.findById(carId) ?: return false

        val advertisement = advertisementRepository.findOneByCarId(car.id!!)

        val hasActiveOrPendingRental =
            advertisement?.let { ad ->
                val rentals = rentalRepository.findAllByAdvertisement(ad)
                rentals.any { it.rentalStatus == RentalStatus.ACTIVE || it.rentalStatus == RentalStatus.PENDING }
            } ?: false

        if (hasActiveOrPendingRental) {
            return false
        }

        advertisementRepository.delete(advertisement?.id!!)

        carImageRepository.deleteAllByCar(car)

        return carRepository.delete(carId)
    }
}

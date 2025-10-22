package rmc.application.usecases.car

import rmc.application.exceptions.CarHasActiveRentalException
import rmc.application.exceptions.CarNotFoundException
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
    operator fun invoke(carId: Int) {
        val car =
            carRepository.findById(carId)
                ?: throw CarNotFoundException("Car with id $carId not found")

        val advertisement = advertisementRepository.findOneByCarId(carId)

        val hasActiveOrPendingRental =
            advertisement?.let { ad ->
                rentalRepository.findAllByAdvertisement(ad).any {
                    it.rentalStatus == RentalStatus.ACTIVE || it.rentalStatus == RentalStatus.PENDING
                }
            } ?: false

        if (hasActiveOrPendingRental) {
            throw CarHasActiveRentalException("Car has active or pending rentals and cannot be deleted")
        }

        advertisementRepository.delete(advertisement?.id!!)

        carImageRepository.deleteAllByCar(car)

        carRepository.delete(carId)
    }
}

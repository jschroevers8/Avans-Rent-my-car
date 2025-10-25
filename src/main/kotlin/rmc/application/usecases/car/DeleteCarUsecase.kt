package rmc.application.usecases.car

import rmc.application.exceptions.CarNotFoundException
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
    operator fun invoke(
        carId: Int,
        userId: Int,
    ) {
        val car =
            carRepository.findById(carId)
                ?: throw CarNotFoundException("Car with id $carId not found")

        car.ensureOwnedBy(userId)

        val advertisement = advertisementRepository.findOneByCarId(carId)

        if (advertisement != null) {
            requireNotNull(advertisement.id) { "Cannot delete advertisement without ID" }

            val rentals = rentalRepository.findAllByAdvertisementId(advertisement.id)

            advertisement.canBeDeleted(rentals)

            advertisementRepository.delete(advertisement.id)
        }

        carImageRepository.deleteAllByCar(car)

        carRepository.delete(carId)
    }
}

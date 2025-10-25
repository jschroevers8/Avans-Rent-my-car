package rmc.application.usecases.advertisement

import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.application.exceptions.CarNotFoundException
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface

class DeleteAdvertisementUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
    private val carRepository: CarRepositoryInterface,
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(
        advertisementId: Int,
        userId: Int,
    ) {
        val advertisement =
            advertisementRepository.findById(advertisementId)
                ?: throw AdvertisementNotFoundException("Advertisement with id $advertisementId not found")

        requireNotNull(advertisement.id) { "Cannot delete advertisement without ID" }

        val car =
            carRepository.findById(advertisement.carId)
                ?: throw CarNotFoundException("Cannot find car with id ${advertisement.carId}")

        car.ensureOwnedBy(userId)

        val rentals = rentalRepository.findAllByAdvertisementId(advertisement.id)

        advertisement.canBeDeleted(rentals)

        advertisementRepository.delete(advertisement.id)
    }
}

package rmc.application.usecases.advertisement

import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface

class DeleteAdvertisementUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(advertisementId: Int): Boolean {
        val advertisement = advertisementRepository.findById(advertisementId) ?: return false

        val rentals = rentalRepository.findAllByAdvertisement(advertisement)

        val hasActiveOrPendingRental =
            rentals.any {
                it.rentalStatus == RentalStatus.ACTIVE || it.rentalStatus == RentalStatus.PENDING
            }

        if (hasActiveOrPendingRental) {
            return false
        }

        return advertisementRepository.delete(advertisement.id!!)
    }
}

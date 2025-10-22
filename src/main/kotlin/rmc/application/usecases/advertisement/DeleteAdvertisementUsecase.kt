package rmc.application.usecases.advertisement

import rmc.application.exceptions.AdvertisementHasActiveRentalException
import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface

class DeleteAdvertisementUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(advertisementId: Int) {
        val advertisement =
            advertisementRepository.findById(advertisementId)
                ?: throw AdvertisementNotFoundException("Advertisement with id $advertisementId not found")

        val rentals = rentalRepository.findAllByAdvertisement(advertisement)

        val hasActiveOrPendingRental =
            rentals.any {
                it.rentalStatus == RentalStatus.ACTIVE || it.rentalStatus == RentalStatus.PENDING
            }

        if (hasActiveOrPendingRental) {
            throw AdvertisementHasActiveRentalException("Advertisement has active or pending rentals and cannot be deleted")
        }

        advertisementRepository.delete(advertisement.id!!)
    }
}

package rmc.application.usecases.rental

import rmc.domain.entities.RentalEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.RentalRepositoryInterface

class CancelRentalUsecase(
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(rentalId: Int): RentalEntity? {
        val rental = rentalRepository.findById(rentalId) ?: return null

        if (rental.rentalStatus == RentalStatus.ACTIVE || rental.rentalStatus == RentalStatus.PENDING) {
            return null
        }

        return rentalRepository.update(rental.copy(rentalStatus = RentalStatus.CANCELLED))
    }
}

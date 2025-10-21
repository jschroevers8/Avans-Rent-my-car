package rmc.application.usecases.rental

import rmc.domain.entities.RentalEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.RentalRepositoryInterface

class ApproveRentalUsecase(
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(rentalId: Int): RentalEntity? {
        val rental = rentalRepository.findById(rentalId) ?: return null

        if (rental.rentalStatus != RentalStatus.PENDING) {
            return null
        }

        return rentalRepository.update(rental.copy(rentalStatus = RentalStatus.ACTIVE))
    }
}

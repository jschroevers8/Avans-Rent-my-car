package rmc.application.usecases.rental

import rmc.application.exceptions.RentalNotFoundException
import rmc.application.exceptions.RentalNotPendingException
import rmc.domain.entities.RentalEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.RentalRepositoryInterface

class ApproveRentalUsecase(
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(rentalId: Int): RentalEntity {
        val rental =
            rentalRepository.findById(rentalId)
                ?: throw RentalNotFoundException("Rental with id $rentalId not found")

        if (rental.rentalStatus != RentalStatus.PENDING) {
            throw RentalNotPendingException("Rental with id $rentalId is not pending and cannot be approved")
        }

        return rentalRepository.update(rental.copy(rentalStatus = RentalStatus.ACTIVE))
    }
}

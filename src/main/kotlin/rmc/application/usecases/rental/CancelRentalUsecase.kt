package rmc.application.usecases.rental

import rmc.application.exceptions.RentalCannotBeCancelledException
import rmc.application.exceptions.RentalNotFoundException
import rmc.domain.entities.RentalEntity
import rmc.domain.entities.RentalStatus
import rmc.domain.repositories.RentalRepositoryInterface

class CancelRentalUsecase(
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(rentalId: Int): RentalEntity {
        val rental =
            rentalRepository.findById(rentalId)
                ?: throw RentalNotFoundException("Rental with id $rentalId not found")

        if (rental.rentalStatus == RentalStatus.ACTIVE || rental.rentalStatus == RentalStatus.PENDING) {
            throw RentalCannotBeCancelledException("Rental with id $rentalId cannot be cancelled because it is ${rental.rentalStatus}")
        }

        return rentalRepository.update(rental.copy(rentalStatus = RentalStatus.CANCELLED))
    }
}

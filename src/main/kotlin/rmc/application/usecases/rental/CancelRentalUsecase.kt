package rmc.application.usecases.rental

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

        rental.ensureStatusNotActiveOrCompleted()

        return rentalRepository.save(rental.copy(rentalStatus = RentalStatus.CANCELLED))
    }
}

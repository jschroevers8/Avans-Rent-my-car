package rmc.application.usecases.rental

import rmc.application.exceptions.RentalNotFoundException
import rmc.domain.entities.RentalEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface

class GetRentalUsecase(
    private val rentalRepository: RentalRepositoryInterface,
    private val rentalTripRepository: RentalTripRepositoryInterface,
) {
    operator fun invoke(rentalId: Int): RentalEntity {
        val rental =
            rentalRepository.findById(rentalId)
                ?: throw RentalNotFoundException("Rental with id $rentalId not found")

        requireNotNull(rental.id) { "Cannot get rental without ID" }

        val trips = rentalTripRepository.findByRentalId(rental.id)
        rental.setTrips(trips)

        return rental
    }
}

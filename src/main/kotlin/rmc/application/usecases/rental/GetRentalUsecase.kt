package rmc.application.usecases.rental

import rmc.domain.entities.RentalEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface

class GetRentalUsecase(
    private val rentalRepository: RentalRepositoryInterface,
    private val rentalTripRepository: RentalTripRepositoryInterface,
) {
    operator fun invoke(rentalId: Int): RentalEntity? {
        val rental = rentalRepository.findById(rentalId) ?: return null

        val trips = rentalTripRepository.findByRentalId(rental.id!!)
        rental.setTrips(trips) // or car.images = images if using a property

        return rental
    }
}

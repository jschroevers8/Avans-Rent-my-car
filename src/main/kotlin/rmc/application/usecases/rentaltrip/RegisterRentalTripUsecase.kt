package rmc.application.usecases.rentaltrip

import rmc.application.exceptions.NotFoundException
import rmc.domain.entities.RentalTripEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface
import rmc.presentation.dto.rentaltrip.RegisterRentalTrip

class RegisterRentalTripUsecase(
    private val rentalTripRepository: RentalTripRepositoryInterface,
    private val rentalRepository: RentalRepositoryInterface,
) {
    suspend fun invoke(request: RegisterRentalTrip): RentalTripEntity {
        if (rentalRepository.findById(request.rentalId) == null) {
            throw NotFoundException("Rental not found")
        }

        val trip =
            RentalTripEntity(
                rentalId = request.rentalId,
                startMileage = request.startMileage,
                endMileage = request.endMileage,
                startDate = request.startDate,
                endDate = request.endDate,
            )

        return rentalTripRepository.save(trip)
    }
}

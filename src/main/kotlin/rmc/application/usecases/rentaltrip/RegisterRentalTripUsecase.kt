package rmc.application.usecases.rentaltrip

import rmc.application.exceptions.RentalNotFoundException
import rmc.domain.entities.RentalTripEntity
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.RentalTripRepositoryInterface
import rmc.presentation.dto.rentaltrip.RegisterRentalTrip

class RegisterRentalTripUsecase(
    private val rentalTripRepository: RentalTripRepositoryInterface,
    private val rentalRepository: RentalRepositoryInterface,
) {
    operator fun invoke(request: RegisterRentalTrip): RentalTripEntity {
        val rentalId = request.rentalId
        if (rentalRepository.findById(rentalId) == null) {
            throw RentalNotFoundException("Rental with id $rentalId not found")
        }

        return rentalTripRepository.save(createRentalTripEntity(request))
    }

    private fun createRentalTripEntity(request: RegisterRentalTrip) =
        RentalTripEntity(
            rentalId = request.rentalId,
            startMileage = request.startMileage,
            endMileage = request.endMileage,
            startDate = request.startDate,
            endDate = request.endDate,
        )
}

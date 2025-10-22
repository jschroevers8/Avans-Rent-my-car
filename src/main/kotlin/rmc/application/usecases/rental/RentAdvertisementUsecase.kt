package rmc.application.usecases.rental

import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.application.exceptions.UserNotFoundException
import rmc.domain.entities.RentalEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.RentalRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.rental.RentAdvertisement

class RentAdvertisementUsecase(
    private val rentalRepository: RentalRepositoryInterface,
    private val userRepository: UserRepositoryInterface,
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {
    operator fun invoke(request: RentAdvertisement): RentalEntity {
        val userId = request.userId
        userRepository.findById(userId)?.let {
            throw UserNotFoundException("User with id $userId not found")
        }

        val advertisementId = request.advertisementId
        if (advertisementRepository.findById(advertisementId) == null) {
            throw AdvertisementNotFoundException("Advertisement with id $advertisementId not found")
        }

        val rental =
            RentalEntity(
                userId = request.userId,
                advertisementId = request.advertisementId,
                rentalStatus = request.rentalStatus,
                pickUpDate = request.pickUpDate,
                returningDate = request.returningDate,
            )

        return rentalRepository.save(rental)
    }
}

package rmc.application.usecases.rental

import rmc.application.exceptions.NotFoundException
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
    suspend fun invoke(request: RentAdvertisement): RentalEntity {
        if (userRepository.findById(request.userId) == null) {
            throw NotFoundException("User not found")
        }

        if (advertisementRepository.findById(request.advertisementId) == null) {
            throw NotFoundException("Advertisement not found")
        }

        val rental =
            RentalEntity(
                userId = request.userId,
                advertisementId = request.advertisementId,
                rentalStatus = request.rentalStatus,
                createdStamp = request.createdStamp,
            )

        return rentalRepository.save(rental)
    }
}

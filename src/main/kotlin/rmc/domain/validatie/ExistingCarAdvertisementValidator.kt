package rmc.domain.validatie

import rmc.domain.exceptions.AdvertisementAlreadyExistsForCarException
import rmc.domain.repositories.AdvertisementRepositoryInterface

class ExistingCarAdvertisementValidator(
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {
    operator fun invoke(carId: Int) {
        if (advertisementRepository.findOneByCarId(carId) != null) {
            throw AdvertisementAlreadyExistsForCarException(
                "An advertisement for car $carId already exists",
            )
        }
    }
}

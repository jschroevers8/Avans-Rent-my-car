package rmc.application.usecases.advertisement

import rmc.application.exceptions.NotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.presentation.dto.advertisement.CreateAdvertisement

class CreateAdvertisementUsecase(
    private val carRepository: CarRepositoryInterface,
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {
    operator fun invoke(advertisementRequest: CreateAdvertisement): AdvertisementEntity {
        if (carRepository.findById(advertisementRequest.carId) == null) {
            throw NotFoundException("Car not found")
        }

        val address =
            AddressEntity(
                city = advertisementRequest.address.city,
                street = advertisementRequest.address.city,
                houseNumber = advertisementRequest.address.houseNumber,
                subHouseNumber = advertisementRequest.address.subHouseNumber,
                postalCode = advertisementRequest.address.postalCode,
            )

        val advertisement =
            AdvertisementEntity(
                carId = advertisementRequest.carId,
                address = address,
                pickUpDate = advertisementRequest.pickUpDate,
                returningDate = advertisementRequest.returningDate,
                price = advertisementRequest.price,
            )

        return advertisementRepository.save(advertisement)
    }
}

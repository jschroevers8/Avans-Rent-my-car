package rmc.application.usecases.advertisement

import rmc.application.exceptions.AdvertisementNotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.presentation.dto.advertisement.UpdateAdvertisement

class UpdateAdvertisementUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {
    operator fun invoke(advertisementRequest: UpdateAdvertisement): AdvertisementEntity {
        val advId = advertisementRequest.carId
        val foundAdvertisement = advertisementRepository.findOneByCarId(advId)

        if (foundAdvertisement == null) {
            throw AdvertisementNotFoundException("Advertisement with id $advId not found")
        }

        val address =
            AddressEntity(
                city = advertisementRequest.address.city,
                street = advertisementRequest.address.street,
                houseNumber = advertisementRequest.address.houseNumber,
                subHouseNumber = advertisementRequest.address.subHouseNumber,
                postalCode = advertisementRequest.address.postalCode,
            )

        val advertisement =
            AdvertisementEntity(
                carId = foundAdvertisement.carId,
                address = address,
                availableFrom = advertisementRequest.availableFrom,
                availableUntil = advertisementRequest.availableUntil,
                price = advertisementRequest.price,
            )

        return advertisementRepository.save(advertisement)
    }
}

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
        val foundAdvertisement =
            advertisementRepository.findOneByCarId(advId)
                ?: throw AdvertisementNotFoundException("Advertisement with id $advId not found")

        return advertisementRepository.save(createAdvertisementEntity(advertisementRequest, foundAdvertisement))
    }

    private fun createAdvertisementEntity(
        request: UpdateAdvertisement,
        foundAdvertisement: AdvertisementEntity,
    ) = AdvertisementEntity(
        carId = foundAdvertisement.carId,
        address =
            with(request.address) {
                AddressEntity(
                    city = city,
                    street = street,
                    houseNumber = houseNumber,
                    subHouseNumber = subHouseNumber,
                    postalCode = postalCode,
                )
            },
        availableFrom = request.availableFrom,
        availableUntil = request.availableUntil,
        price = request.price,
    )
}

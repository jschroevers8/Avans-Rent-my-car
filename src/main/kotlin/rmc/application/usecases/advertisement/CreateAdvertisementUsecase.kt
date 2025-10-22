package rmc.application.usecases.advertisement

import rmc.application.exceptions.AdvertisementAlreadyExistsForCarException
import rmc.application.exceptions.CarNotFoundException
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
        val carId = advertisementRequest.carId
        if (carRepository.findById(carId) == null) {
            throw CarNotFoundException("Car with id $carId not found")
        }

        val existingAdvertisement = advertisementRepository.findOneByCarId(advertisementRequest.carId)
        if (existingAdvertisement != null) {
            throw AdvertisementAlreadyExistsForCarException("An advertisement for this car already exists")
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
                carId = advertisementRequest.carId,
                address = address,
                availableFrom = advertisementRequest.availableFrom,
                availableUntil = advertisementRequest.availableUntil,
                price = advertisementRequest.price,
            )

        return advertisementRepository.save(advertisement)
    }
}

package rmc.application.usecases.advertisement

import rmc.application.exceptions.CarNotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.validations.ExistingCarAdvertisementValidator
import rmc.presentation.dto.advertisement.CreateAdvertisement

class CreateAdvertisementUsecase(
    private val carRepository: CarRepositoryInterface,
    private val advertisementRepository: AdvertisementRepositoryInterface,
    private val existingCarAdvertisementValidator: ExistingCarAdvertisementValidator,
) {
    operator fun invoke(
        advertisementRequest: CreateAdvertisement,
        userId: Int,
    ): AdvertisementEntity {
        val car =
            carRepository.findById(advertisementRequest.carId)
                ?: throw CarNotFoundException("Car with id ${advertisementRequest.carId} not found")

        requireNotNull(car.id) { "Cannot create advertisement without Car ID" }

        car.ensureOwnedBy(userId)

        existingCarAdvertisementValidator(car.id)

        return advertisementRepository.save(createAdvertisementEntity(advertisementRequest))
    }

    private fun createAdvertisementEntity(request: CreateAdvertisement) =
        AdvertisementEntity(
            carId = request.carId,
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

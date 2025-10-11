package rmc.application.services

import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.Column
import rmc.application.exceptions.NotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.entities.CarEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.domain.repositories.CarRepositoryInterface
import rmc.domain.repositories.UserRepositoryInterface
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.dto.car.CreateCar

class AdvertisementService (
    private val carRepository: CarRepositoryInterface,
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {
//    suspend fun getAllAdvertisements(): List<AdvertisementEntity> = advertisementRepository.getAll()

    suspend fun create(advertisementRequest: CreateAdvertisement): AdvertisementEntity {
        if (carRepository.findById(advertisementRequest.carId) == null) {
            throw NotFoundException("Car not found")
        }

        val address = AddressEntity(
            city = advertisementRequest.address.city,
            street = advertisementRequest.address.city,
            houseNumber = advertisementRequest.address.houseNumber,
            subHouseNumber = advertisementRequest.address.subHouseNumber,
            postalCode = advertisementRequest.address.postalCode,
        )

        val advertisement = AdvertisementEntity(
            carId = advertisementRequest.carId,
            address = address,
            pickUpDate = advertisementRequest.pickUpDate,
            returningDate = advertisementRequest.returningDate,
            price = advertisementRequest.price,
        )

        return advertisementRepository.save(advertisement)
    }
}
package rmc.domain.repositories

import rmc.domain.entities.AdvertisementEntity

interface AdvertisementRepositoryInterface {
    fun getAllAvailable(): List<AdvertisementEntity>

    fun findOneByCarId(carId: Int): AdvertisementEntity?

    fun findById(id: Int): AdvertisementEntity?

    fun save(advertisement: AdvertisementEntity): AdvertisementEntity

    fun delete(id: Int): Boolean
}

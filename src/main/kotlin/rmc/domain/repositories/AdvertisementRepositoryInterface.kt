package rmc.domain.repositories

import rmc.domain.entities.AdvertisementEntity

interface AdvertisementRepositoryInterface {
    fun findById(id: Int): AdvertisementEntity?
    fun save(advertisement: AdvertisementEntity): AdvertisementEntity
}
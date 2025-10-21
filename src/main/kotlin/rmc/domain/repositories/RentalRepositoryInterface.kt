package rmc.domain.repositories

import rmc.domain.entities.AdvertisementEntity
import rmc.domain.entities.RentalEntity

interface RentalRepositoryInterface {
    fun findById(id: Int): RentalEntity?

    fun findAllByAdvertisement(advertisement: AdvertisementEntity): List<RentalEntity>

    fun getAll(): List<RentalEntity>

    fun save(rental: RentalEntity): RentalEntity

    fun update(rental: RentalEntity): RentalEntity

    fun delete(id: Int): Boolean
}

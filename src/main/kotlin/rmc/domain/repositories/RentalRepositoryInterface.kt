package rmc.domain.repositories

import rmc.domain.entities.RentalEntity

interface RentalRepositoryInterface {
    fun findById(id: Int): RentalEntity?

    fun findAllByAdvertisementId(advertisementId: Int): List<RentalEntity>

    fun save(rental: RentalEntity): RentalEntity
}

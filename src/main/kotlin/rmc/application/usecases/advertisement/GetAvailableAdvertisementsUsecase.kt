package rmc.application.usecases.advertisement

import org.jetbrains.exposed.sql.transactions.transaction
import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface
import rmc.infrastructure.repositories.AdvertisementRepository

class GetAvailableAdvertisementsUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {

    fun invoke(): List<AdvertisementEntity> = advertisementRepository.getAll()
}
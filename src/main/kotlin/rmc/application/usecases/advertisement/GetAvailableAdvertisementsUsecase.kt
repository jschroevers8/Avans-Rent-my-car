package rmc.application.usecases.advertisement

import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface

class GetAvailableAdvertisementsUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
) {
    operator fun invoke(): List<AdvertisementEntity> = advertisementRepository.getAll()
}

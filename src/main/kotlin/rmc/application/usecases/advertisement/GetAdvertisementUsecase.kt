package rmc.application.usecases.advertisement

import rmc.domain.entities.AdvertisementEntity
import rmc.domain.repositories.AdvertisementRepositoryInterface

class GetAdvertisementUsecase(
    private val advertisementRepository: AdvertisementRepositoryInterface,
){
    fun invoke(advertisementId: Int): AdvertisementEntity? = advertisementRepository.findById(advertisementId)
}
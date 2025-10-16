package rmc.presentation.controllers

import rmc.application.services.AdvertisementService
import rmc.presentation.dto.advertisement.AdvertisementResponse
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.mappers.toResponse

class AdvertisementController(
    private val advertisementService: AdvertisementService,
) {
    fun getAllAdvertisements(): List<AdvertisementResponse> = advertisementService.getAllAdvertisements().map { it.toResponse() }

    fun findAllAdvertisementById(id: Int): AdvertisementResponse? = advertisementService.findAdvertisementById(id)?.toResponse()

    fun createAdvertisement(request: CreateAdvertisement): AdvertisementResponse {
        val created = advertisementService.create(request)

        return created.toResponse()
    }
}

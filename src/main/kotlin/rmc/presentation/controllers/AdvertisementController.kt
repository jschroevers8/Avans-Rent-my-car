package rmc.presentation.controllers

import rmc.application.services.AdvertisementService
import rmc.application.services.UserService
import rmc.domain.entities.UserEntity
import rmc.presentation.dto.advertisement.AdvertisementResponse
import rmc.presentation.dto.advertisement.CreateAdvertisement
import rmc.presentation.dto.user.CreateUser
import rmc.presentation.dto.user.UserResponse
import rmc.presentation.mappers.toEntity
import rmc.presentation.mappers.toResponse

class AdvertisementController(private val advertisementService: AdvertisementService) {


    suspend fun createAdvertisement(request: CreateAdvertisement): AdvertisementResponse {
        val created = advertisementService.create(request)

        return created.toResponse()
    }
}
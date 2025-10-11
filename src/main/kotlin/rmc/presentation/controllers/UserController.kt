package rmc.presentation.controllers

import rmc.application.services.UserService
import rmc.domain.entities.UserEntity
import rmc.presentation.dto.user.CreateUser
import rmc.presentation.dto.user.UserResponse
import rmc.presentation.mappers.toEntity
import rmc.presentation.mappers.toResponse

class UserController(private val userService: UserService) {


    suspend fun createUser(request: CreateUser): UserResponse {
        val created = userService.createUser(request)

        return created.toResponse()
    }

    suspend fun getUser(id: Int): UserResponse? {
        val user = userService.getUser(id)

        return user?.toResponse()
    }
}
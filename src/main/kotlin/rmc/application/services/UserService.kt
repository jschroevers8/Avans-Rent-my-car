package rmc.application.services

import rmc.application.exceptions.NotFoundException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.CarEntity
import rmc.domain.entities.UserEntity
import rmc.domain.repositories.UserRepositoryInterface
import rmc.infrastructure.tables.AddressTable
import rmc.infrastructure.tables.UserTable
import rmc.presentation.dto.user.CreateUser

class UserService(
    private val userRepository: UserRepositoryInterface
) {
    suspend fun createUser(userRequest: CreateUser): UserEntity {

        if (userRepository.findByEmail(userRequest.email) != null) {
            throw NotFoundException("User with this email already exists")
        }

        val address = AddressEntity(
            city = userRequest.address.city,
            street = userRequest.address.city,
            houseNumber = userRequest.address.houseNumber,
            subHouseNumber = userRequest.address.subHouseNumber,
            postalCode = userRequest.address.postalCode,
        )

        val user = UserEntity(
            userType = userRequest.userType,
            address = address,
            email = userRequest.email,
            password = userRequest.password,
            firstName = userRequest.firstName,
            lastName = userRequest.lastName,
            phone = userRequest.phone,
            userPoints = userRequest.userPoints,
        )

        return userRepository.save(user)
    }

    suspend fun getUser(id: Int): UserEntity? =
        userRepository.findById(id)
}
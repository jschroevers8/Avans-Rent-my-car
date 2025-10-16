package rmc.application.usecases.user

import rmc.domain.entities.UserEntity
import rmc.domain.repositories.UserRepositoryInterface

class LoginUsecase(
    private val userRepository: UserRepositoryInterface,
) {
    fun invoke(id: Int): UserEntity? = userRepository.findById(id)
}
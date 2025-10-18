package rmc.application.usecases.user

import rmc.domain.entities.UserEntity
import rmc.domain.repositories.UserRepositoryInterface

class LoginUsecase(
    private val userRepository: UserRepositoryInterface,
) {
    fun invoke(email: String): UserEntity? = userRepository.findByEmail(email)
}

package rmc.application.usecases.user

import rmc.domain.entities.UserEntity
import rmc.domain.repositories.UserRepositoryInterface

class LoginUsecase(
    private val userRepository: UserRepositoryInterface,
) {
    operator fun invoke(
        email: String,
        password: String,
    ): UserEntity? {
        val user = userRepository.findByEmail(email) ?: return null
        return if (user.password == password) user else null
    }
}

package rmc.application.usecases.user

import rmc.application.exceptions.InvalidCredentialsException
import rmc.domain.entities.UserEntity
import rmc.domain.repositories.UserRepositoryInterface

class LoginUsecase(
    private val userRepository: UserRepositoryInterface,
) {
    operator fun invoke(
        email: String,
        password: String,
    ): UserEntity {
        val user =
            userRepository.findByEmail(email)
                ?: throw InvalidCredentialsException("Invalid email or password")

        if (user.password != password) {
            throw InvalidCredentialsException("Invalid email or password")
        }

        return user
    }
}

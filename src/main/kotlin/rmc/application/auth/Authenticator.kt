package rmc.application.auth

import rmc.domain.entities.UserEntity

class Authenticator(
    private val tokenGenerator: TokenGeneratorInterface,
) {
    fun generateToken(user: UserEntity): String {
        return tokenGenerator.generate(user.id!!)
    }
}

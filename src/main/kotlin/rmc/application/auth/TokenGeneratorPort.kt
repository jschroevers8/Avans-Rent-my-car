package rmc.application.auth

interface TokenGeneratorPort {
    fun generate(userId: Int): String
}

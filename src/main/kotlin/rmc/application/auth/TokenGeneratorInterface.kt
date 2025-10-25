package rmc.application.auth

interface TokenGeneratorInterface {
    fun generate(userId: Int): String
}

package rmc.infrastructure.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import rmc.application.auth.TokenGeneratorPort
import java.util.Date

class JwtTokenGenerator(
    private val jwtSecret: String,
    private val jwtAudience: String,
    private val jwtDomain: String,
) : TokenGeneratorPort {

    override fun generate(userId: Int): String {
        return JWT.create()
            .withAudience(jwtAudience)
            .withIssuer(jwtDomain)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + 24*60*60000))
            .sign(Algorithm.HMAC256(jwtSecret))
    }
}
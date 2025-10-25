package rmc.infrastructure.auth

import com.auth0.jwt.JWT
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class JwtTokenGeneratorTest {
    private val secret = "mySecret123"
    private val audience = "myAudience"
    private val domain = "myDomain"

    private val generator = JwtTokenGenerator(secret, audience, domain)

    @Test
    fun `generate should create a valid JWT token with correct claims`() {
        val userId = 42
        val token = generator.generate(userId)

        // Token is not null of leeg
        assertNotNull(token)
        assertTrue(token.isNotEmpty())

        // Decode het token zonder te verifiëren
        val decoded = JWT.decode(token)

        // Claims controleren
        assertEquals(userId, decoded.getClaim("userId").asInt())
        assertEquals(audience, decoded.audience.first())
        assertEquals(domain, decoded.issuer)

        // Expiratie check (ongeveer 24 uur)
        val expectedExpiry = System.currentTimeMillis() + 24 * 60 * 60000
        val actualExpiry = decoded.expiresAt.time
        val diff = kotlin.math.abs(actualExpiry - expectedExpiry)
        assertTrue(diff < 1000 * 60, "Token expiration should be roughly 24 hours from now")
    }

    @Test
    fun `generate should produce different tokens for different userIds`() {
        val token1 = generator.generate(1)
        val token2 = generator.generate(2)

        assertTrue(token1 != token2)
    }
}

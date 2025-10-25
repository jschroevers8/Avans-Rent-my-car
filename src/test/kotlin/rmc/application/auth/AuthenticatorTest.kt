package rmc.application.auth

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.UserEntity
import rmc.domain.entities.UserType
import kotlin.test.Test
import kotlin.test.assertEquals

class AuthenticatorTest {
    private val sampleAddress =
        AddressEntity(
            id = 1,
            city = "Amsterdam",
            street = "Damstraat",
            houseNumber = 10,
            subHouseNumber = "A",
            postalCode = "1012JS",
        )

    @Test
    fun `generateToken should return token from TokenGenerator`() {
        // Arrange
        val tokenGenerator = mockk<TokenGeneratorInterface>()
        val authenticator = Authenticator(tokenGenerator)
        val user =
            UserEntity(
                id = 1,
                userType = UserType.CUSTOMER,
                address = sampleAddress,
                email = "test@example.com",
                password = "password123",
                firstName = "John",
                lastName = "Doe",
                phone = "0612345678",
                userPoints = 100,
            )
        val expectedToken = "mocked_token"

        every { tokenGenerator.generate(user.id!!) } returns expectedToken

        // Act
        val actualToken = authenticator.generateToken(user)

        // Assert
        assertEquals(expectedToken, actualToken)
        verify(exactly = 1) { tokenGenerator.generate(user.id!!) }
    }
}

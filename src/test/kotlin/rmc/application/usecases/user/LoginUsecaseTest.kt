package rmc.application.usecases.user

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import rmc.application.exceptions.InvalidCredentialsException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.UserEntity
import rmc.domain.entities.UserType
import rmc.domain.repositories.UserRepositoryInterface

class LoginUsecaseTest {
    private lateinit var userRepository: UserRepositoryInterface
    private lateinit var loginUsecase: LoginUsecase

    @BeforeEach
    fun setUp() {
        userRepository = mockk()
        loginUsecase = LoginUsecase(userRepository)
    }

    @Test
    fun `should return user when email and password are correct`() {
        val address = AddressEntity(
            street = "fooStreet",
            city = "fooCity",
            postalCode = "1011AA",
            houseNumber = 1,
            subHouseNumber = "foo",
        )

        val user = UserEntity(
            id = 1,
            email = "test@example.com",
            password = "password123",
            userType = UserType.CUSTOMER,
            address = address,
            firstName = "test",
            lastName = "test",
            phone = "1234567890",
            userPoints = 1
            )

        every { userRepository.findByEmail("test@example.com") } returns user

        val result = loginUsecase("test@example.com", "password123")

        assertEquals(user, result)
        verify { userRepository.findByEmail("test@example.com") }
    }

    @Test
    fun `should throw InvalidCredentialsException when email does not exist`() {
        every { userRepository.findByEmail("wrong@example.com") } returns null

        val exception =
            assertThrows(InvalidCredentialsException::class.java) {
                loginUsecase("wrong@example.com", "password123")
            }

        assertEquals("Invalid email or password", exception.message)
    }

    @Test
    fun `should throw InvalidCredentialsException when password is incorrect`() {
        val address = AddressEntity(
            street = "fooStreet",
            city = "fooCity",
            postalCode = "1011AA",
            houseNumber = 1,
            subHouseNumber = "foo",
        )

        val user = UserEntity(
            id = 1,
            email = "test@example.com",
            password = "password123",
            userType = UserType.CUSTOMER,
            address = address,
            firstName = "test",
            lastName = "test",
            phone = "1234567890",
            userPoints = 1
        )

        every { userRepository.findByEmail("test@example.com") } returns user

        val exception =
            assertThrows(InvalidCredentialsException::class.java) {
                loginUsecase("test@example.com", "wrongpassword")
            }

        assertEquals("Invalid email or password", exception.message)
    }
}

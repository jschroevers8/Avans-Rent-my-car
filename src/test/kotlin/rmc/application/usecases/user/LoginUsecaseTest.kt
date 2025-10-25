package rmc.application.usecases.user

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.mindrot.jbcrypt.BCrypt
import rmc.application.exceptions.InvalidCredentialsException
import rmc.domain.entities.AddressEntity
import rmc.domain.entities.UserEntity
import rmc.domain.entities.UserType
import rmc.domain.repositories.UserRepositoryInterface

class LoginUsecaseTest {
    private lateinit var userRepository: UserRepositoryInterface
    private lateinit var loginUsecase: LoginUsecase

    @BeforeTest
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

        val hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt())

        val user = UserEntity(
            id = 1,
            email = "test@example.com",
            password = hashedPassword,
            userType = UserType.CUSTOMER,
            address = address,
            firstName = "test",
            lastName = "test",
            phone = "1234567890",
            userPoints = 1,
        )

        every { userRepository.findByEmail("test@example.com") } returns user

        val result = loginUsecase("test@example.com", "password123")

        assertEquals(user, result)
        verify { userRepository.findByEmail("test@example.com") }
    }

    @Test
    fun `should throw InvalidCredentialsException when email does not exist`() {
        every { userRepository.findByEmail("wrong@example.com") } returns null

        val exception = assertFailsWith<InvalidCredentialsException> {
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

        val hashedPassword = BCrypt.hashpw("password123", BCrypt.gensalt())

        val user = UserEntity(
            id = 1,
            email = "test@example.com",
            password = hashedPassword,
            userType = UserType.CUSTOMER,
            address = address,
            firstName = "test",
            lastName = "test",
            phone = "1234567890",
            userPoints = 1,
        )

        every { userRepository.findByEmail("test@example.com") } returns user

        val exception = assertFailsWith<InvalidCredentialsException> {
            loginUsecase("test@example.com", "wrongpassword")
        }

        assertEquals("Invalid email or password", exception.message)
    }
}
